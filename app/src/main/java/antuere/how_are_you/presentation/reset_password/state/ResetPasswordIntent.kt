package antuere.how_are_you.presentation.reset_password.state


sealed interface ResetPasswordIntent {
    data class ResetBtnClicked(val userEmail: String) : ResetPasswordIntent
    data class EmailChanged(val value: String) : ResetPasswordIntent
}
