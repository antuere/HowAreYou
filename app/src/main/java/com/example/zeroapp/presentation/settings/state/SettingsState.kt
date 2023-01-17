package com.example.zeroapp.presentation.settings.state

import antuere.domain.dto.Settings
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState

interface SettingsState {

    object Loading : SettingsState

    data class Loaded(
        val settings: Settings,
        val userPinCode: String,
        val userNickname: String
    ) : SettingsState
}


data class SettingState(
    val isLoading: Boolean = true,
    val settings: Settings? = null,
    val userPinCode: String = "",
    val userNickname: String = ""
)