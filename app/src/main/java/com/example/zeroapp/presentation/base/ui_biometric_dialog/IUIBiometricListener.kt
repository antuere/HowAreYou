package com.example.zeroapp.presentation.base.ui_biometric_dialog


interface IUIBiometricListener {

    fun onBiometricAuthFailed()

    fun onBiometricAuthSuccess()

    fun noneEnrolled()
}