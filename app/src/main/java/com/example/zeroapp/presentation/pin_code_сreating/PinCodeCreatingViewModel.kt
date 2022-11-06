package com.example.zeroapp.presentation.pin_code_сreating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Settings
import antuere.domain.usecases.GetSettingsUseCase
import antuere.domain.usecases.SaveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinCodeCreatingViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {

    private var _userPassword = MutableLiveData<String>()
    val userPassword: LiveData<String>
        get() = _userPassword

    private var _pinCodeCirclesState = MutableLiveData<PinCodeCirclesState>()
    val pinCodeCirclesState: LiveData<PinCodeCirclesState>
        get() = _pinCodeCirclesState

    private var _settings = MutableLiveData<Settings>()
    val settings: LiveData<Settings>
        get() = _settings

    private var _isNavigateUp = MutableLiveData<Boolean>()
    val isNavigateUp: LiveData<Boolean>
        get() = _isNavigateUp

    init {
        getSettings()
    }

    private lateinit var num1: String
    private lateinit var num2: String
    private lateinit var num3: String
    private lateinit var num4: String

    private var currentNumbers = mutableListOf<String>()
    private var savedPassword = String()

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
                _userPassword.value = num1 + num2 + num3 + num4
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase(Unit).collectLatest {
                _settings.postValue(it)
            }
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            _settings.value!!.isPinCodeEnabled = true
            saveSettingsUseCase(_settings.value!!)

            delay(150)

            _isNavigateUp.value = true
        }
    }

    fun doneNavigationUp() {
        _isNavigateUp.value = false
    }
}