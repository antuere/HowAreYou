package antuere.how_are_you.presentation.screens.sign_up_with_email.state

import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface SignUpEmailSideEffect {
    object NavigateToSettings : SignUpEmailSideEffect
    object ClearFocus : SignUpEmailSideEffect
    data class Snackbar(val message: UiText) : SignUpEmailSideEffect
}