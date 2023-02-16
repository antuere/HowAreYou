package antuere.how_are_you.presentation.screens.sign_up_with_email.state

data class SignUpEmailState(
    val email: String = "",
    val nickName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isShowProgressIndicator: Boolean = false,
)
