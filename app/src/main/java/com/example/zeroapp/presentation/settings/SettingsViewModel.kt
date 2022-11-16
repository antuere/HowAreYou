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
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.IUIBiometricListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.pin_code_сreating.IPinCodeCreatingListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val uiBiometricDialog: UIBiometricDialog
) : ViewModel(), IUIDialogAction {

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _userNickname = MutableLiveData<String?>()
    val userNickname: LiveData<String?>
        get() = _userNickname

    private var _settings = MutableLiveData<Settings?>()
    val settings: LiveData<Settings?>
        get() = _settings

    private var _biometricAuthState = MutableLiveData<BiometricAuthState?>()
    val biometricAuthState: LiveData<BiometricAuthState?>
        get() = _biometricAuthState

    private var _isPinCodeCreated = MutableLiveData<Boolean?>()
    val isPinCodeCreated: LiveData<Boolean?>
        get() = _isPinCodeCreated

    private var _isStartSetBiometric = MutableLiveData(false)
    val isStartSetBiometric: LiveData<Boolean>
        get() = _isStartSetBiometric

    private var _isStartSetPinCode = MutableLiveData(false)
    val isStartSetPinCode: LiveData<Boolean>
        get() = _isStartSetPinCode

    private var _biometricAvailableState = MutableLiveData<BiometricsAvailableState?>()
    val biometricAvailableState: LiveData<BiometricsAvailableState?>
        get() = _biometricAvailableState

    private var isShowDialogSignOut = false

    init {
        getUserNickname()
        getSettings()
        getLastDay()
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
            _biometricAuthState.value = BiometricAuthState.Error
        }

        override fun onBiometricAuthSuccess() {
            viewModelScope.launch {
                doneAuthByBiometricUseCase(Unit)
            }
            _biometricAuthState.value = BiometricAuthState.Successful
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
        viewModelScope.launch {
            getUserNicknameUseCase(Unit).collectLatest {
                _userNickname.postValue(it)
            }
        }
    }

    private fun getLastDay() {
        viewModelScope.launch {
            getLastDayUseCase(Unit).collectLatest {
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
                neutralButton = UIDialog.UiButton(
                    text = R.string.dialog_delete_local_data_neutral,
                    onClick = {
                        _uiDialog.value = null
                    }
                )
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
        viewModelScope.launch {
            getSettingsUseCase(Unit).collectLatest {
                _settings.postValue(it)
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