package com.example.zeroapp.presentation.pin_code_creation

import androidx.compose.runtime.Stable

@Stable
enum class PinCodeCirclesState {
    NONE, FIRST, SECOND, THIRD, FOURTH, WRONG_PIN, CORRECT_PIN
}