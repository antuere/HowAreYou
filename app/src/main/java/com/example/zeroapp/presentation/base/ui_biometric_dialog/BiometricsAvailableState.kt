package com.example.zeroapp.presentation.base.ui_biometric_dialog

import androidx.annotation.StringRes

sealed class BiometricsAvailableState {
    object Available : BiometricsAvailableState()
    object NoHardware : BiometricsAvailableState()
    object NoneEnrolled : BiometricsAvailableState()
    data class SomeError(@StringRes val messageResId: Int) : BiometricsAvailableState()
}
