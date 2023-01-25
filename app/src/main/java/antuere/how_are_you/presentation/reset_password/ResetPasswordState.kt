package antuere.how_are_you.presentation.reset_password

import antuere.how_are_you.presentation.base.ui_text.UiText


sealed class ResetPasswordState(val message: UiText) {

    class Successful(message: UiText) : ResetPasswordState(message)

    class EmptyFields(message: UiText) : ResetPasswordState(message)

    class ErrorFromFireBase(message: UiText) : ResetPasswordState(message)
}