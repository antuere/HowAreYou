package antuere.how_are_you.presentation.sign_in_methods.state

import antuere.how_are_you.presentation.base.ui_text.UiText
import com.google.android.gms.auth.api.signin.GoogleSignInClient

sealed interface SignInMethodsSideEffect {
    data class GoogleSignInDialog(val signInClient: GoogleSignInClient) : SignInMethodsSideEffect
    data class Snackbar(val message: UiText) : SignInMethodsSideEffect
    object NavigateUp : SignInMethodsSideEffect
    object NavigateToEmailMethod : SignInMethodsSideEffect
}