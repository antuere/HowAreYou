package antuere.how_are_you.presentation.screens.account_settings.state

import antuere.domain.util.Constants
import antuere.how_are_you.presentation.base.ui_text.UiText

data class AccountSettingsState(
    val isLoading: Boolean = true,
    val isShowProgressIndicator: Boolean = false,
    val isShowReauthDialog: Boolean = false,
    val isShowAccDeleteDialog: Boolean = false,
    val isShowErrorInTextField: Boolean = false,
    val isSaveLocalData: Boolean = false,
    val userNickname: String = Constants.USER_NOT_AUTH,
    val userEmail: String = "",
    val userEnteredPassword: String = "",
    val errorMessage: UiText = UiText.String(""),
)
