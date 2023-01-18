package com.example.zeroapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.dto.Settings
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.util.Constants
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.IUIBiometricListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.settings.state.SettingsSideEffect
import com.example.zeroapp.presentation.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val settingsRepository: SettingsRepository,
    private val toggleBtnRepository: ToggleBtnRepository,
    private val authenticationManager: AuthenticationManager,
    val uiBiometricDialog: UIBiometricDialog
) : ContainerHost<SettingsState, SettingsSideEffect>, ViewModel() {

    override val container: Container<SettingsState, SettingsSideEffect> =
        container(SettingsState.Loading)

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _userNickname = MutableStateFlow(Constants.USER_NOT_AUTH)
    val userNickname: StateFlow<String>
        get() = _userNickname

    private var _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?>
        get() = _settings

    private var _savedPinCode = MutableStateFlow<String?>(null)
    val savedPinCode: StateFlow<String?>
        get() = _savedPinCode

    private var _biometricAuthState = MutableStateFlow<BiometricAuthState?>(null)
    val biometricAuthState: StateFlow<BiometricAuthState?>
        get() = _biometricAuthState

    private var _biometricAvailableState = MutableStateFlow<BiometricsAvailableState?>(null)
    val biometricAvailableState: StateFlow<BiometricsAvailableState?>
        get() = _biometricAvailableState

    private var isShowDialogSignOut = false

    init {
        getSettings()
        getSavedPinCode()
        getUserNickname()
        checkIsHasDayEntity()
        checkBiometricsAvailable()
    }

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() {
            _biometricAuthState.value = BiometricAuthState.Error
        }

        override fun onBiometricAuthSuccess() {
            _biometricAuthState.value =
                BiometricAuthState.Success(UiText.StringResource(R.string.biom_auth_create_success))
        }

        override fun noneEnrolled() {
            _biometricAvailableState.value =
                BiometricsAvailableState.NoneEnrolled(UiText.StringResource(R.string.biometric_none_enroll))
        }
    }


    private fun checkBiometricsAvailable() {
        _biometricAvailableState.value =
            uiBiometricDialog.deviceHasBiometricHardware
    }

    private fun checkIsHasDayEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.getLastDay().collectLatest {
                isShowDialogSignOut = it != null
            }
        }
    }

    fun onClickSignOut() {
        if (isShowDialogSignOut) {
            _uiDialog.value = UIDialog(
                title = R.string.dialog_delete_local_data_title,
                desc = R.string.dialog_delete_local_data_desc,
                icon = R.drawable.ic_delete_black,
                positiveButton = UIDialog.UiButton(
                    text = R.string.dialog_delete_local_data_positive,
                    onClick = {
                        signOut(isSaveDayEntities = true)
                        _uiDialog.value = null
                    }),
                negativeButton = UIDialog.UiButton(
                    text = R.string.dialog_delete_local_data_negative,
                    onClick = {
                        signOut(isSaveDayEntities = false)
                        _uiDialog.value = null
                    }),
                dismissAction = { _uiDialog.value = null }
            )
        } else {
            signOut(false)
        }
    }

    private fun signOut(isSaveDayEntities: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isSaveDayEntities) {
               dayRepository.deleteAllDaysLocal()
            }
            authenticationManager.signOut()
            toggleBtnRepository.resetToggleButtonState()
            settingsRepository.resetUserNickname()
        }
    }

    private fun getSettings() = intent {
        viewModelScope.launch(Dispatchers.IO) {

//            combine(
//                getSettingsUseCase(Unit),
//                getSavedPinCodeUseCase(Unit),
//                getUserNicknameUseCase(Unit),
//                ::Triple
//            ).collectLatest {
//                val settings = it.first
//                val savedPin = it.second
//                val nickname = it.third
//
//
//            }

            settingsRepository.getSettings().collectLatest {
                _settings.value = it
            }
        }
    }

    private fun getUserNickname() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getUserNickname().collectLatest {
                _userNickname.value = it
            }
        }
    }

    private fun getSavedPinCode() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getPinCode().collectLatest {
                _savedPinCode.value = it
            }
        }
    }

    fun saveSettings(isUseBiometric: Boolean, isUsePinCode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value!!.isBiometricEnabled = isUseBiometric
            _settings.value!!.isPinCodeEnabled = isUsePinCode

            settingsRepository.saveSettings(_settings.value!!)
        }
    }

    fun saveShowWorriedDialog(isShowWorriedDialog: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value!!.isShowWorriedDialog = isShowWorriedDialog

            settingsRepository.saveSettings(_settings.value!!)
        }
    }

    fun resetBiometricAuthAndSaveSettings(isUseBiometric: Boolean, isUsePinCode: Boolean) {
        nullifyBiometricAuthState()
        checkBiometricsAvailable()
        saveSettings(isUseBiometric, isUsePinCode)

    }

    fun resetPinCodeAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.resetPinCode()
        }
    }


    fun nullifyBiometricAvailableState() {
        _biometricAvailableState.value = null
    }

    fun nullifyBiometricAuthState() {
        _biometricAuthState.value = null
    }
}