package antuere.how_are_you.presentation.screens.account_settings.state

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed interface AccountSettingsIntent {
    object SignOutBtnClicked : AccountSettingsIntent
    object DeleteAccountBtnClicked : AccountSettingsIntent
    object PasswordEntered : AccountSettingsIntent
    object DeleteDataBtnClicked : AccountSettingsIntent
    object ReauthPasswordDialogClosed : AccountSettingsIntent
    data class ConfirmedPasswordChanged(val value: String) : AccountSettingsIntent
    data class GoogleAccAdded(val task: Task<GoogleSignInAccount>) : AccountSettingsIntent
}