package antuere.how_are_you.presentation.sign_up_with_email

import antuere.how_are_you.presentation.base.ui_text.UiText

sealed class SignUpState {

    object Successful : SignUpState()

    data class EmptyFields(val message: UiText) : SignUpState()

    data class PasswordsError(val message: UiText) : SignUpState()

    data class ErrorFromFireBase(val message: UiText) : SignUpState()
}
