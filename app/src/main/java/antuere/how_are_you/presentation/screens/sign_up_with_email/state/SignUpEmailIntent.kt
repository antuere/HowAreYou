package antuere.how_are_you.presentation.screens.sign_up_with_email.state

sealed interface SignUpEmailIntent {
    data class EmailChanged(val value: String) : SignUpEmailIntent
    data class NicknameChanged(val value: String) : SignUpEmailIntent
    data class ConfirmPasswordChanged(val value: String) : SignUpEmailIntent
    data class PasswordChanged(val value: String) : SignUpEmailIntent
    object SignInBtnClicked : SignUpEmailIntent
}