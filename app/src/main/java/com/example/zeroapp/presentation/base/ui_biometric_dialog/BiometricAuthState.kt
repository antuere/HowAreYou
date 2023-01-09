package com.example.zeroapp.presentation.base.ui_biometric_dialog

import com.example.zeroapp.presentation.base.ui_text.UiText

sealed class BiometricAuthState {
    class Success(val message: UiText) : BiometricAuthState()
    object Error : BiometricAuthState()
}