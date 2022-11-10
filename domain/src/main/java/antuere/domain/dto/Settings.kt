package antuere.domain.dto

data class Settings(
    var isBiometricEnabled: Boolean,
    var isPinCodeEnabled: Boolean,
    var isShowWorriedDialog: Boolean
)
