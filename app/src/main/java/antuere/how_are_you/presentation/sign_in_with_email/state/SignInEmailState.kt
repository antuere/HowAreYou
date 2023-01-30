package antuere.how_are_you.presentation.sign_in_with_email.state

data class SignInEmailState(
    val email: String = "",
    val password: String = "",
    val isShowProgressIndicator: Boolean = false
)
