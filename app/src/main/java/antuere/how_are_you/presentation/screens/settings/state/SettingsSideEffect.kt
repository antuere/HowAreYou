package antuere.how_are_you.presentation.screens.settings.state

import android.content.Intent
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface SettingsSideEffect {
    data class BiometricNoneEnroll(val enrollIntent: Intent) : SettingsSideEffect
    data class Snackbar(val message: UiText) : SettingsSideEffect
    data class Dialog(val uiDialog: UIDialog) : SettingsSideEffect
    data class BiometricDialog(val dialog: UIBiometricDialog) : SettingsSideEffect
    object NavigateToSignIn : SettingsSideEffect
    object NavigateToAccountSettings : SettingsSideEffect
    object NavigateToDayCustomization : SettingsSideEffect
    object NavigateToPrivacyPolicyWebSite : SettingsSideEffect
    object HideNavBar : SettingsSideEffect
    object ShowNavBar : SettingsSideEffect
}