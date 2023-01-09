package com.example.zeroapp.presentation.sign_in_with_email

import com.example.zeroapp.presentation.base.ui_text.UiText

sealed class SignInState {
    object Successful : SignInState()

    data class EmptyFields(val message: UiText) : SignInState()

    data class ErrorFromFireBase(val message: UiText) : SignInState()
}
