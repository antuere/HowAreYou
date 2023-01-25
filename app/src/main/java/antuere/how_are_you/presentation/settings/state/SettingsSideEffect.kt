package antuere.how_are_you.presentation.settings.state

import android.content.Intent
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface SettingsSideEffect {

    object NavigateToSignIn : SettingsSideEffect

    object ShowBottomSheet : SettingsSideEffect

    data class BiometricNoneEnroll(val enrollIntent: Intent) : SettingsSideEffect

    data class Snackbar(val message: UiText) : SettingsSideEffect

    data class Dialog(val uiDialog: UIDialog) : SettingsSideEffect

    data class BiometricDialog(val dialog: UIBiometricDialog) : SettingsSideEffect

}