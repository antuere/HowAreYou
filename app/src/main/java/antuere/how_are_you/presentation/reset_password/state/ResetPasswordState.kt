package antuere.how_are_you.presentation.reset_password.state

data class ResetPasswordState(
    val email: String = "",
    val isShowProgressIndicator: Boolean = false
)