package com.example.zeroapp.presentation.secure_entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Settings
import antuere.domain.usecases.authentication.SignOutUseCase
import antuere.domain.usecases.days_entities.DeleteAllDaysLocalUseCase
import antuere.domain.usecases.privacy.DoneAuthByBiometricUseCase
import com.example.zeroapp.R
import antuere.domain.usecases.privacy.DoneAuthByPinUseCase
import antuere.domain.usecases.user_settings.DeleteAllSettingsUseCase
import antuere.domain.usecases.user_settings.GetSavedPinCodeUseCase
import antuere.domain.usecases.user_settings.GetSettingsUseCase
import antuere.domain.usecases.user_settings.SaveSettingsUseCase
import com.example.zeroapp.presentation.base.ui_biometric_dialog.IUIBiometricListener
import com.example.zeroapp.presentation.pin_code_creation.PinCodeCirclesState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureEntryViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getSavedPinCodeUseCase: GetSavedPinCodeUseCase,
    private val deleteAllSettingsUseCase: DeleteAllSettingsUseCase,
    private val deleteAllDaysLocalUseCase: DeleteAllDaysLocalUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val doneAuthByBiometricUseCase: DoneAuthByBiometricUseCase,
    private val doneAuthByPinUseCase: DoneAuthByPinUseCase,
    val uiBiometricDialog: UIBiometricDialog
) : ViewModel() {

    private lateinit var num1: String
    private lateinit var num2: String
    private lateinit var num3: String
    private lateinit var num4: String

    private var currentNumbers = mutableListOf<String>()

    private var _uiDialog = MutableStateFlow<UIDialogCompose?>(null)
    val uiDialog: StateFlow<UIDialogCompose?>
        get() = _uiDialog

    private var _userPinCode = MutableStateFlow<String?>(null)

    private var _biometricAuthState = MutableStateFlow<BiometricAuthState?>(null)
    val biometricAuthState: StateFlow<BiometricAuthState?>
        get() = _biometricAuthState

    private var _isShowBiometricAuth = MutableStateFlow(false)
    val isShowBiometricAuth: StateFlow<Boolean>
        get() = _isShowBiometricAuth

    private var _pinCodeCirclesState = MutableStateFlow(PinCodeCirclesState.NONE)
    val pinCodeCirclesState: StateFlow<PinCodeCirclesState>
        get() = _pinCodeCirclesState

    private var _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?>
        get() = _settings

    private var _isShowErrorSnackBar = MutableStateFlow(false)
    val isShowErrorSnackBar: StateFlow<Boolean>
        get() = _isShowErrorSnackBar

    private var _isNavigateToHomeScreen = MutableStateFlow(false)
    val isNavigateToHomeScreen: StateFlow<Boolean>
        get() = _isNavigateToHomeScreen

    private var _biometricAvailableState = MutableStateFlow<BiometricsAvailableState?>(null)
    val biometricAvailableState: StateFlow<BiometricsAvailableState?>
        get() = _biometricAvailableState

    private var savedPinCode: String? = null

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() {
            _biometricAuthState.value = BiometricAuthState.ERROR
        }

        override fun onBiometricAuthSuccess() {
            _pinCodeCirclesState.value = PinCodeCirclesState.ALL

            viewModelScope.launch {
                doneAuthByPinUseCase(Unit)
                doneAuthByBiometricUseCase(Unit)
            }

            _biometricAuthState.value = BiometricAuthState.SUCCESS
            saveSettings()
            _isNavigateToHomeScreen.value = true
        }

        override fun noneEnrolled() {
            _biometricAvailableState.value = BiometricsAvailableState.NoneEnrolled
        }
    }


    init {
        getSettings()
        checkBiometricsAvailable()
        getSavedPinCode()
    }

    private fun getSettings() {
        viewModelScope.launch {
            _settings.value = getSettingsUseCase(Unit).first()
            delay(350)
            if (_settings.value!!.isBiometricEnabled) {
                _isShowBiometricAuth.value = true
            }
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            _settings.value!!.isBiometricEnabled = true
            saveSettingsUseCase(_settings.value!!)
        }
    }

    private fun getSavedPinCode() {
        viewModelScope.launch {
            savedPinCode = getSavedPinCodeUseCase(Unit).first()
        }
    }

    private fun checkBiometricsAvailable() {
        _biometricAvailableState.value =
            uiBiometricDialog.deviceHasBiometricHardware
    }


    fun onClickNumber(value: String) {
        when (value) {
            "0" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "1" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "2" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "3" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "4" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "5" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "6" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "7" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "8" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            "9" -> {
                currentNumbers.add(value)
                checkPassword(currentNumbers)
            }
            else -> throw IllegalArgumentException("Invalid number")

        }
    }

    private fun checkPassword(list: List<String>) {
        when (list.size) {
            1 -> {
                num1 = list[0]
                _pinCodeCirclesState.value = PinCodeCirclesState.FIRST
            }
            2 -> {
                num2 = list[1]
                _pinCodeCirclesState.value = PinCodeCirclesState.SECOND
            }
            3 -> {
                num3 = list[2]
                _pinCodeCirclesState.value = PinCodeCirclesState.THIRD
            }
            4 -> {
                num4 = list[3]
                _pinCodeCirclesState.value = PinCodeCirclesState.FOURTH
                _userPinCode.value = num1 + num2 + num3 + num4

                validateEnteredPinCode(_userPinCode.value!!)
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }

    private fun resetAllUserData() {
        viewModelScope.launch {
            signOutUseCase(Unit)
            deleteAllSettingsUseCase(Unit)
            deleteAllDaysLocalUseCase(Unit)

            delay(150)
            _isNavigateToHomeScreen.value = true
        }

    }

    private fun validateEnteredPinCode(pinCode: String) {
        if (pinCode == savedPinCode) {
            _isNavigateToHomeScreen.value = true
        } else {
            _isShowErrorSnackBar.value = true
            resetAllPinCodeStates()
        }
    }

    fun resetAllPinCodeStates() {
        viewModelScope.launch {
            _userPinCode.value = null
            currentNumbers.clear()
            _pinCodeCirclesState.value = PinCodeCirclesState.NONE
        }

    }

    fun onClickSignOut() {
        _uiDialog.value = UIDialogCompose(
            title = R.string.dialog_sign_out_title,
            desc = R.string.dialog_sign_out_desc,
            icon = R.drawable.ic_log_out,
            positiveButton = UIDialogCompose.UiButton(
                text = R.string.dialog_sign_out_positive,
                onClick = {
                    resetAllUserData()
                    _uiDialog.value = null
                }),
            negativeButton = UIDialogCompose.UiButton(
                text = R.string.dialog_sign_out_negative,
                onClick = {
                    _uiDialog.value = null
                }),
            dismissAction = {
                _uiDialog.value = null
            }

        )
    }

    fun onClickBiometricBtn() {
        _isShowBiometricAuth.value = true
    }

    fun resetIsShowErrorSnackBar() {
        _isShowErrorSnackBar.value = false
    }

    fun nullifyBiometricAvailableState() {
        _biometricAvailableState.value = null
    }

    fun navigateToHomeScreen() {
        _isNavigateToHomeScreen.value = true

    }

    fun resetIsNavigateToHomeScreen() {
        _isNavigateToHomeScreen.value = false
    }

    fun resetIsShowBiometricAuth() {
        _isShowBiometricAuth.value = false
    }

}
