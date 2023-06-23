package antuere.how_are_you.presentation.screens.account_settings.ui_compose

import androidx.compose.runtime.Composable
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.PopUpProgressIndicator
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsIntent
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsState
import antuere.how_are_you.presentation.screens.account_settings.ui_compose.components.AccountDeleteDialog
import antuere.how_are_you.presentation.screens.account_settings.ui_compose.components.MainContentAccountSettings
import antuere.how_are_you.presentation.screens.account_settings.ui_compose.components.ReauthWithPasswordDialog

@Composable
fun AccountSettingsScreenContent(
    viewState: () -> AccountSettingsState,
    onIntent: (AccountSettingsIntent) -> Unit,
) {
    when {
        viewState().isLoading -> {
            FullScreenProgressIndicator()
        }

        viewState().isShowProgressIndicator -> {
            PopUpProgressIndicator()
        }

        viewState().isShowReauthDialog -> {
            ReauthWithPasswordDialog(
                onDismissRequest = { onIntent(AccountSettingsIntent.ReauthPasswordDialogClosed) },
                onPasswordChanged = { onIntent(AccountSettingsIntent.ConfirmedPasswordChanged(it)) },
                onClickDeleteAcc = { onIntent(AccountSettingsIntent.PasswordEntered) },
                viewState = { viewState() }
            )
        }

        viewState().isShowAccDeleteDialog -> {
            AccountDeleteDialog(
                onDismissRequest = { onIntent(AccountSettingsIntent.AccDeleteDialogClosed) },
                onSaveLocalDataSettingChanged = { onIntent(AccountSettingsIntent.SaveLocalDataSettingChanged) },
                onClickStartReauth = { onIntent(AccountSettingsIntent.StartReauthClicked) },
                isSaveLocalData = viewState().isSaveLocalData
            )
        }
    }

    if (!viewState().isLoading) {
        MainContentAccountSettings(
            nickname = viewState().userNickname,
            email = viewState().userEmail,
            onIntent = onIntent
        )
    }
}