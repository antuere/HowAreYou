package antuere.how_are_you.presentation.screens.sign_in_with_email.state

import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface SignInEmailSideEffect {
    object NavigateToSettings : SignInEmailSideEffect
    object NavigateToResetPassword : SignInEmailSideEffect
    object NavigateToSignUp : SignInEmailSideEffect
    data class Snackbar(val message: UiText) : SignInEmailSideEffect
}