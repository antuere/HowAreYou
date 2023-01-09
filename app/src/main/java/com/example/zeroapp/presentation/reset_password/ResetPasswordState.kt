package com.example.zeroapp.presentation.reset_password

import com.example.zeroapp.presentation.base.ui_text.UiText


sealed class ResetPasswordState(val message: UiText) {

    class Successful(message: UiText) : ResetPasswordState(message)

    class EmptyFields(message: UiText) : ResetPasswordState(message)

    class ErrorFromFireBase(message: UiText) : ResetPasswordState(message)
}