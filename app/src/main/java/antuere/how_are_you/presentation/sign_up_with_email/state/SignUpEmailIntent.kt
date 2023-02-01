package antuere.how_are_you.presentation.sign_up_with_email.state

sealed interface SignUpEmailIntent {
    data class EmailChanged(val value: String) : SignUpEmailIntent
    data class NicknameChanged(val value: String) : SignUpEmailIntent
    data class ConfirmPasswordChanged(val value: String) : SignUpEmailIntent
    data class PasswordChanged(val value: String) : SignUpEmailIntent
    data class SignInBtnClicked(
        val email: String,
        val nickName: String,
        val password: String,
        val confirmPassword: String,
    ) : SignUpEmailIntent
}