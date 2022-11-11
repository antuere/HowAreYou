package com.example.zeroapp.presentation.base.ui_biometric_dialog

sealed class BiometricAuthState {
    object Successful : BiometricAuthState()
    object Error : BiometricAuthState()
}