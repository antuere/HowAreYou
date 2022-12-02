package com.example.zeroapp.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.zeroapp.presentation.pin_code_сreating.IPinCodeCreatingListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
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

    private var _userNickname = MutableStateFlow<String>(Constants.USER_NOT_AUTH)
    val userNickname: StateFlow<String>
        get() = _userNickname

    private var _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?>
        get() = _settings

    private var _biometricAuthState = MutableStateFlow<BiometricAuthState?>(null)
    val biometricAuthState: StateFlow<BiometricAuthState?>
        get() = _biometricAuthState

    private var _isPinCodeCreated = MutableStateFlow<Boolean?>(null)
    val isPinCodeCreated: StateFlow<Boolean?>
        get() = _isPinCodeCreated

    private var _isStartSetBiometric = MutableStateFlow(false)
    val isStartSetBiometric: StateFlow<Boolean>
        get() = _isStartSetBiometric

    private var _isStartSetPinCode = MutableStateFlow(false)
    val isStartSetPinCode: StateFlow<Boolean>
        get() = _isStartSetPinCode

    private var _biometricAvailableState = MutableStateFlow<BiometricsAvailableState?>(null)
    val biometricAvailableState: StateFlow<BiometricsAvailableState?>
        get() = _biometricAvailableState

    private var isShowDialogSignOut = false

    init {
        getUserNickname()
        getSettings()
        checkIsHasDayEntity()
        checkBiometricsAvailable()
    }

    val pinCodeCreatingListener = object : IPinCodeCreatingListener {

        override fun pinCodeCreated() {
            viewModelScope.launch {
                doneAuthByPinUseCase(Unit)
            }
            _isPinCodeCreated.value = true
        }

        override fun pinCodeNotCreated() {
            _isPinCodeCreated.value = false
        }
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

    fun setBiometricAuth() {
        viewModelScope.launch {
            if (!checkAuthByBiometricUseCase(Unit)) {
                _isStartSetBiometric.value = true
            }
        }
    }

    fun setPinCodeAuth() {
        viewModelScope.launch {
            if (!checkAuthByPinUseCase(Unit) && !checkAuthByBiometricUseCase(Unit)) {
                _isStartSetPinCode.value = true
            }
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
            _isPinCodeCreated.value = null
            delay(100)

            resetPinCodeUseCase(Unit)
        }
    }


    fun resetIsStartSetBiometric() {
        _isStartSetBiometric.value = false
    }

    fun resetIsStartSetPinCode() {
        _isStartSetPinCode.value = false
    }

    fun nullifyBiometricAuthState() {
        _biometricAuthState.value = null
    }

    fun nullifyIsPinCodeCreated() {
        _isPinCodeCreated.value = null
    }
}