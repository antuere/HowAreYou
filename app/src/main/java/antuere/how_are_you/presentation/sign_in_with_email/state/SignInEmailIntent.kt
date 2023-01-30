package antuere.how_are_you.presentation.sign_in_with_email.state


sealed interface SignInEmailIntent {
    data class SignInBtnClicked(val email: String, val password: String) : SignInEmailIntent
    data class EmailChanged(val value: String) : SignInEmailIntent
    data class PasswordChanged(val value: String) : SignInEmailIntent
    object SignUpBtnClicked : SignInEmailIntent
    object ResetPassBtnClicked : SignInEmailIntent
}