package antuere.how_are_you.presentation.reset_password.state

import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface ResetPasswordSideEffect {
    object NavigateUp : ResetPasswordSideEffect
    data class Snackbar(val message: UiText) : ResetPasswordSideEffect
}