package antuere.domain.dto

data class Settings(
    val isBiometricEnabled: Boolean,
    val isPinCodeEnabled: Boolean,
    val isShowWorriedDialog: Boolean
)
