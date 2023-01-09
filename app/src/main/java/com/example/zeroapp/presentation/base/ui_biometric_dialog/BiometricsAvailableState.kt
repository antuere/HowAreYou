package com.example.zeroapp.presentation.base.ui_biometric_dialog

import com.example.zeroapp.presentation.base.ui_text.UiText

sealed class BiometricsAvailableState {
    object Available : BiometricsAvailableState()
    object NoHardware : BiometricsAvailableState()
    data class NoneEnrolled(val message: UiText) : BiometricsAvailableState()
    data class SomeError(val message: UiText) : BiometricsAvailableState()
}
