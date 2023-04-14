package antuere.how_are_you.presentation.screens.reset_password.state

import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface ResetPasswordSideEffect {
    object NavigateUp : ResetPasswordSideEffect
    object ClearFocus : ResetPasswordSideEffect
    data class Snackbar(val message: UiText) : ResetPasswordSideEffect
}