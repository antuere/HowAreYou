package antuere.how_are_you.presentation.base.ui_biometric_dialog


interface IUIBiometricListener {

    fun onBiometricAuthFailed()

    fun onBiometricAuthSuccess()

    fun noneEnrolled()
}