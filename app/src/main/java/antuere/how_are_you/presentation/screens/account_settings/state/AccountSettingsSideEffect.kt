package antuere.how_are_you.presentation.screens.account_settings.state

import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText
import com.google.android.gms.auth.api.signin.GoogleSignInClient

sealed interface AccountSettingsSideEffect {
    data class Snackbar(val message: UiText) : AccountSettingsSideEffect
    data class Dialog(val uiDialog: UIDialog) : AccountSettingsSideEffect
    data class GoogleSignInDialog(val signInClient: GoogleSignInClient) : AccountSettingsSideEffect
    object NavigateToSettings : AccountSettingsSideEffect
}