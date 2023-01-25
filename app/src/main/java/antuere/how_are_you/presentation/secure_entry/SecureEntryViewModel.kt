package antuere.how_are_you.presentation.secure_entry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.dto.Settings
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.base.ui_biometric_dialog.IUIBiometricListener
import antuere.how_are_you.presentation.pin_code_creation.PinCodeCirclesState
import antuere.how_are_you.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureEntryViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    val uiBiometricDialog: UIBiometricDialog
) : ViewModel() {

    private lateinit var num1: String
    private lateinit var num2: String
    private lateinit var num3: String
    private lateinit var num4: String

    private var currentNumbers = mutableListOf<String>()

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _isShowBiometricAuth = MutableStateFlow(false)
    val isShowBiometricAuth: StateFlow<Boolean>
        get() = _isShowBiometricAuth

    private var _pinCodeCirclesState = MutableStateFlow(PinCodeCirclesState.NONE)
    val pinCodeCirclesState: StateFlow<PinCodeCirclesState>
        get() = _pinCodeCirclesState

    private var _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?>
        get() = _settings

    private var _isShowErrorMessage = MutableStateFlow(false)
    val isShowErrorMessage: StateFlow<Boolean>
        get() = _isShowErrorMessage

    private var _isNavigateToHomeScreen = MutableStateFlow(false)
    val isNavigateToHomeScreen: StateFlow<Boolean>
        get() = _isNavigateToHomeScreen

    private var _biometricAvailableState = MutableStateFlow<BiometricsAvailableState?>(null)
    val biometricAvailableState: StateFlow<BiometricsAvailableState?>
        get() = _biometricAvailableState

    private var savedPinCode: String? = null
    private var userPinCode = MutableStateFlow<String?>(null)

    private var wrongPinAnimationJob: Job? = null

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() {
        }

        override fun onBiometricAuthSuccess() {
            _pinCodeCirclesState.value = PinCodeCirclesState.CORRECT_PIN

            saveSettings()
            _isNavigateToHomeScreen.value = true
        }

        override fun noneEnrolled() {
            _biometricAvailableState.value =
                BiometricsAvailableState.NoneEnrolled(UiText.StringResource(R.string.biometric_none_enroll))
        }
    }


    init {
        getSettings()
        checkBiometricsAvailable()
        getSavedPinCode()
    }

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value = settingsRepository.getSettings().first()
            delay(350)
            if (_settings.value!!.isBiometricEnabled) {
                _isShowBiometricAuth.value = true
            }
        }
    }

    fun saveSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value!!.isBiometricEnabled = true
            settingsRepository.saveSettings(_settings.value!!)
        }
    }

    private fun getSavedPinCode() {
        viewModelScope.launch(Dispatchers.IO) {
            savedPinCode = settingsRepository.getPinCode().first()
        }
    }

    private fun checkBiometricsAvailable() {
//        _biometricAvailableState.value =
//            uiBiometricDialog.deviceHasBiometricHardware
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
        wrongPinAnimationJob?.cancel()

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
                userPinCode.value = num1 + num2 + num3 + num4

                validateEnteredPinCode(userPinCode.value!!)
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }

    private fun resetAllUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationManager.signOut()
            dayRepository.deleteAllDaysLocal()
            settingsRepository.resetAllSettings()

            delay(150)
            _isNavigateToHomeScreen.value = true
        }

    }

    private fun validateEnteredPinCode(pinCode: String) {
        if (pinCode == savedPinCode) {
            _isNavigateToHomeScreen.value = true
            _pinCodeCirclesState.value = PinCodeCirclesState.CORRECT_PIN
        } else {
            wrongPinAnimationJob = viewModelScope.launch {
                _isShowErrorMessage.value = true
                _pinCodeCirclesState.value = PinCodeCirclesState.WRONG_PIN
                userPinCode.value = null
                currentNumbers.clear()

                delay(500)

                _pinCodeCirclesState.value = PinCodeCirclesState.NONE
            }
        }
    }

    fun resetAllPinCodeStates() {
        userPinCode.value = null
        currentNumbers.clear()
        _pinCodeCirclesState.value = PinCodeCirclesState.NONE
    }

    fun onClickSignOut() {
        _uiDialog.value = UIDialog(
            title = R.string.dialog_sign_out_title,
            desc = R.string.dialog_sign_out_desc,
            icon = R.drawable.ic_log_out,
            positiveButton = UIDialog.UiButton(
                text = R.string.dialog_sign_out_positive,
                onClick = {
                    resetAllUserData()
                    _uiDialog.value = null
                }),
            negativeButton = UIDialog.UiButton(
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

    fun resetIsShowError() {
        _isShowErrorMessage.value = false
    }

    fun nullifyBiometricAvailableState() {
        _biometricAvailableState.value = null
    }

    fun resetIsNavigateToHomeScreen() {
        _isNavigateToHomeScreen.value = false
    }

    fun resetIsShowBiometricAuth() {
        _isShowBiometricAuth.value = false
    }

}
