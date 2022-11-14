package com.example.zeroapp.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Settings
import antuere.domain.usecases.*
import antuere.domain.usecases.authentication.SignOutUseCase
import antuere.domain.usecases.privacy.DoneAuthByBiometricUseCase
import antuere.domain.usecases.privacy.DoneAuthByPinUseCase
import antuere.domain.usecases.privacy.ResetAuthByBiometricUseCase
import antuere.domain.usecases.privacy.ResetAuthByPinUseCase
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.IUIBiometricListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.pin_code_сreating.IPinCodeCreatingListener
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase,
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
    private val uiBiometricDialog: UIBiometricDialog
) : ViewModel() {

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


    init {
        getUserNickname()
        getSettings()
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

    fun onSignOutClicked() {
        viewModelScope.launch {
            signOutUseCase(Unit)
            resetUserNicknameUseCase(Unit)
            refreshRemoteDataUseCase(Unit)
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
        if (!privacyManager.isUserAuthByBiometric) {
            _isStartSetBiometric.value = true
        }
    }

    fun setPinCodeAuth() {
        if (!privacyManager.isUserAuthByPinCode && !privacyManager.isUserAuthByBiometric) {
            _isStartSetPinCode.value = true
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