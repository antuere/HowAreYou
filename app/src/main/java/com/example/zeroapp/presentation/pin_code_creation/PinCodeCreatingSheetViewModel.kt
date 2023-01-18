package com.example.zeroapp.presentation.pin_code_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinCodeCreatingSheetViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var _userPinCode = MutableStateFlow<String?>(null)

    private var _pinCodeCirclesState = MutableStateFlow(PinCodeCirclesState.NONE)
    val pinCodeCirclesState: StateFlow<PinCodeCirclesState>
        get() = _pinCodeCirclesState

    private var _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?>
        get() = _settings

    private var _isPinCodeCreated = MutableStateFlow(false)
    val isPinCodeCreated: StateFlow<Boolean>
        get() = _isPinCodeCreated


    init {
        getSettings()
    }

    private var num1: String? = null
    private var num2: String? = null
    private var num3: String? = null
    private var num4: String? = null

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
            else -> throw IllegalArgumentException("Invalid number: $value")

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
                pinCodeCreated()
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.getSettings().collectLatest {
                _settings.value = it
            }
        }
    }

    private fun pinCodeCreated() {
        viewModelScope.launch {
            saveSettings()
            savePinCode(_userPinCode.value!!)
            delay(100)

            _isPinCodeCreated.value = true
        }
    }

    fun resetAllPinCodeStates() {
        _userPinCode.value = null
        currentNumbers.clear()
        _pinCodeCirclesState.value = PinCodeCirclesState.NONE
    }

    private fun savePinCode(pinCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.savePinCode(pinCode)
        }
    }

    private fun saveSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            _settings.value!!.isPinCodeEnabled = true
            settingsRepository.saveSettings(_settings.value!!)
        }
    }

    fun resetIsPinCodeCreated() {
        _isPinCodeCreated.value = false
    }
}