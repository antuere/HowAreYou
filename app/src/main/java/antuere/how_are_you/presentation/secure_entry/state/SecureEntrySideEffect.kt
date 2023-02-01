package antuere.how_are_you.presentation.secure_entry.state

import android.content.Intent
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface SecureEntrySideEffect {
    object NavigateToHome : SecureEntrySideEffect
    data class Snackbar(val message: UiText) : SecureEntrySideEffect
    data class Dialog(val uiDialog: UIDialog) : SecureEntrySideEffect
    data class BiometricNoneEnroll(val enrollIntent: Intent) : SecureEntrySideEffect
    data class BiometricDialog(val dialog: UIBiometricDialog) : SecureEntrySideEffect
}