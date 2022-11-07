package com.example.zeroapp.presentation.secure_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Settings
import antuere.domain.usecases.GetSavedPinCodeUseCase
import antuere.domain.usecases.GetSettingsUseCase
import com.example.zeroapp.presentation.base.PrivacyManager
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCirclesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureEntryViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getSavedPinCodeUseCase: GetSavedPinCodeUseCase,
    private val privacyManager: PrivacyManager
) : ViewModel() {

    private var _userPinCode = MutableLiveData<String?>()
    val userPinCode: LiveData<String?>
        get() = _userPinCode

    private var _pinCodeCirclesState = MutableLiveData<PinCodeCirclesState>()
    val pinCodeCirclesState: LiveData<PinCodeCirclesState>
        get() = _pinCodeCirclesState

    private var _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings>
        get() = _settings


    private var _savedPinCode = MutableLiveData<String>()
    val savedPinCode: LiveData<String>
        get() = _savedPinCode

    private var _isCorrectPinCode = MutableLiveData<Boolean>()
    val isCorrectPinCode: LiveData<Boolean>
        get() = _isCorrectPinCode

    init {
        getSettings()
        getSavedPinCode()
    }

    private fun getSavedPinCode() {
        viewModelScope.launch {
            getSavedPinCodeUseCase.invoke(Unit).collectLatest {
                _savedPinCode.postValue(it)
            }
        }
    }

    private lateinit var num1: String
    private lateinit var num2: String
    private lateinit var num3: String
    private lateinit var num4: String

    private var currentNumbers = mutableListOf<String>()


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
                _pinCodeCirclesState.value = PinCodeCirclesState.IsShowOne
            }
            2 -> {
                num2 = list[1]
                _pinCodeCirclesState.value = PinCodeCirclesState.IsShowTwo
            }
            3 -> {
                num3 = list[2]
                _pinCodeCirclesState.value = PinCodeCirclesState.IsShowThree
            }
            4 -> {
                num4 = list[3]
                _pinCodeCirclesState.value = PinCodeCirclesState.IsShowFour
                _userPinCode.value = num1 + num2 + num3 + num4
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }

    fun validateEnteredPinCode(pinCode: String) {
        if (pinCode == savedPinCode.value) {
            privacyManager.doneAuthUserByPinCode()
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
            _pinCodeCirclesState.value = PinCodeCirclesState.IsShowNone
        }

    }

    private fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase(Unit).collectLatest {
                _settings.postValue(it)
            }
        }
    }
}
