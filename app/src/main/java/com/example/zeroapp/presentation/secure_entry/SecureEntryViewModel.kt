package com.example.zeroapp.presentation.secure_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCirclesState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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
    private val uiBiometricDialog: UIBiometricDialog
) : ViewModel(), IUIDialogAction {

    private lateinit var num1: String
    private lateinit var num2: String
    private lateinit var num3: String
    private lateinit var num4: String
    private var currentNumbers = mutableListOf<String>()

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _userPinCode = MutableLiveData<String?>()
    val userPinCode: LiveData<String?>
        get() = _userPinCode

    private var _biometricAuthState = MutableLiveData<BiometricAuthState?>()
    val biometricAuthState: LiveData<BiometricAuthState?>
        get() = _biometricAuthState

    private var _isShowBiometricAuth = MutableLiveData(false)
    val isShowBiometricAuth: LiveData<Boolean>
        get() = _isShowBiometricAuth

    private var _pinCodeCirclesState = MutableLiveData<PinCodeCirclesState>()
    val pinCodeCirclesState: LiveData<PinCodeCirclesState>
        get() = _pinCodeCirclesState

    private var _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings>
        get() = _settings

    private var _isCorrectPinCode = MutableLiveData<Boolean>()
    val isCorrectPinCode: LiveData<Boolean>
        get() = _isCorrectPinCode

    private var _isNavigateToHomeFragment = MutableLiveData(false)
    val isNavigateToHomeFragment: LiveData<Boolean>
        get() = _isNavigateToHomeFragment

    private var _biometricAvailableState = MutableLiveData<BiometricsAvailableState?>()
    val biometricAvailableState: LiveData<BiometricsAvailableState?>
        get() = _biometricAvailableState

    private var isBiomAuthCanceled = false
    private var savedPinCode = MutableLiveData<String>()

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
            getSettingsUseCase(Unit).collectLatest {
                _settings.postValue(it)
            }
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            _settings.value!!.isBiometricEnabled = true
            saveSettingsUseCase(_settings.value!!)
        }
    }

    private fun getSavedPinCode() {
        viewModelScope.launch {
            getSavedPinCodeUseCase.invoke(Unit).collectLatest {
                savedPinCode.postValue(it)
            }
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
            _isNavigateToHomeFragment.value = true
        }

    }

    fun validateEnteredPinCode(pinCode: String) {
        if (pinCode == savedPinCode.value) {
            if (isBiomAuthCanceled) {
                viewModelScope.launch {
                    doneAuthByPinUseCase(Unit)
                    doneAuthByBiometricUseCase(Unit)
                }
            } else {
                viewModelScope.launch {
                    doneAuthByPinUseCase(Unit)
                }
            }
            _isCorrectPinCode.value = true
        } else {
            _isCorrectPinCode.value = false
        }
    }

    fun resetEnteredPinCode() {
        viewModelScope.launch {
            delay(100)

            _userPinCode.value = null
            currentNumbers.clear()
            _pinCodeCirclesState.value = PinCodeCirclesState.NONE
        }

    }

    fun onClickBiometricBtn() {
        showBiometricAuth(false)
        _isShowBiometricAuth.value = false
        saveSettings()
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
                })

        )
    }

    fun showBiometricAuth(withDelay: Boolean) {
        if (withDelay) {
            viewModelScope.launch {
                delay(1000)
                _isShowBiometricAuth.value = true
            }
        } else {
            _isShowBiometricAuth.value = true
        }
    }

    fun biomAuthDialogCanceled() {
        isBiomAuthCanceled = true
    }

    fun navigateToHomeFragment() {
        _isNavigateToHomeFragment.value = true
    }

}
