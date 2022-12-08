package com.example.zeroapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Settings
import antuere.domain.usecases.authentication.SignOutUseCase
import antuere.domain.usecases.days_entities.DeleteAllDaysLocalUseCase
import antuere.domain.usecases.days_entities.GetLastDayUseCase
import antuere.domain.usecases.privacy.*
import antuere.domain.usecases.user_settings.*
import antuere.domain.util.Constants
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.IUIBiometricListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getLastDayUseCase: GetLastDayUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getSavedPinCodeUseCase: GetSavedPinCodeUseCase,
    private val getUserNicknameUseCase: GetUserNicknameUseCase,
    private val resetPinCodeUseCase: ResetPinCodeUseCase,
    private val resetUserNicknameUseCase: ResetUserNicknameUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val doneAuthByPinUseCase: DoneAuthByPinUseCase,
    private val doneAuthByBiometricUseCase: DoneAuthByBiometricUseCase,
    private val resetAuthByBiometricUseCase: ResetAuthByBiometricUseCase,
    private val resetAuthByPinUseCase: ResetAuthByPinUseCase,
    private val checkAuthByBiometricUseCase: CheckAuthByBiometricUseCase,
    private val checkAuthByPinUseCase: CheckAuthByPinUseCase,
    private val deleteAllDaysLocalUseCase: DeleteAllDaysLocalUseCase,
    val uiBiometricDialog: UIBiometricDialog
) : ViewModel() {

    private var _uiDialog = MutableStateFlow<UIDialogCompose?>(null)
    val uiDialog: StateFlow<UIDialogCompose?>
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
        getUserNickname()
        getSettings()
        getSavedPinCode()
        checkIsHasDayEntity()
        checkBiometricsAvailable()
    }

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() {
            _biometricAuthState.value = BiometricAuthState.ERROR
        }

        override fun onBiometricAuthSuccess() {
            viewModelScope.launch {
                doneAuthByBiometricUseCase(Unit)
            }
            _biometricAuthState.value = BiometricAuthState.SUCCESS
        }

        override fun noneEnrolled() {
            _biometricAvailableState.value = BiometricsAvailableState.NoneEnrolled
        }
    }


    private fun checkBiometricsAvailable() {
        _biometricAvailableState.value =
            uiBiometricDialog.deviceHasBiometricHardware
    }

    private fun getUserNickname() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserNicknameUseCase(Unit).collectLatest {
                _userNickname.value = it
            }
        }
    }

    private fun checkIsHasDayEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            getLastDayUseCase(Unit).collectLatest {
                isShowDialogSignOut = it != null
            }
        }
    }

    fun onClickSignOut() {
        if (isShowDialogSignOut) {
            _uiDialog.value = UIDialogCompose(
                title = R.string.dialog_delete_local_data_title,
                desc = R.string.dialog_delete_local_data_desc,
                icon = R.drawable.ic_delete_black,
                positiveButton = UIDialogCompose.UiButton(
                    text = R.string.dialog_delete_local_data_positive,
                    onClick = {
                        signOut(isSaveDayEntities = true)
                        _uiDialog.value = null
                    }),
                negativeButton = UIDialogCompose.UiButton(
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
        viewModelScope.launch {
            if (!isSaveDayEntities) {
                deleteAllDaysLocalUseCase(Unit)
            }
            signOutUseCase(Unit)
            resetUserNicknameUseCase(Unit)
        }
    }

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            getSettingsUseCase(Unit).collectLatest {
                _settings.value = it
            }
        }
    }

    private fun getSavedPinCode() {
        viewModelScope.launch(Dispatchers.IO) {
            getSavedPinCodeUseCase(Unit).collectLatest {
                _savedPinCode.value = it
            }
        }
    }

    fun saveSettings(isUseBiometric: Boolean, isUsePinCode: Boolean) {
        viewModelScope.launch {
            _settings.value!!.isBiometricEnabled = isUseBiometric
            _settings.value!!.isPinCodeEnabled = isUsePinCode

            saveSettingsUseCase(_settings.value!!)
        }
    }

    fun saveShowWorriedDialog(isShowWorriedDialog: Boolean) {
        viewModelScope.launch {
            _settings.value!!.isShowWorriedDialog = isShowWorriedDialog

            saveSettingsUseCase(_settings.value!!)
        }
    }

    fun resetBiometricAuthAndSaveSettings(isUseBiometric: Boolean, isUsePinCode: Boolean) {
        viewModelScope.launch {
            resetAuthByBiometricUseCase(Unit)
            nullifyBiometricAuthState()
            checkBiometricsAvailable()
            saveSettings(isUseBiometric, isUsePinCode)
        }
    }

    fun resetPinCodeAuth() {
        viewModelScope.launch {
            resetAuthByPinUseCase(Unit)
            delay(100)

            resetPinCodeUseCase(Unit)
        }
    }


    fun nullifyBiometricAvailableState() {
        _biometricAvailableState.value = null
    }

    fun nullifyBiometricAuthState() {
        _biometricAuthState.value = null
    }
}