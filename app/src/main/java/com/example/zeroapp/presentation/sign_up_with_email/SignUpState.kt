package com.example.zeroapp.presentation.sign_up_with_email

import androidx.annotation.StringRes

sealed class SignUpState {

    object Successful : SignUpState()

    data class EmptyFields(@StringRes val res: Int) : SignUpState()

    data class PasswordsError(@StringRes val res: Int) : SignUpState()

    data class ErrorFromFireBase(var message: String) : SignUpState()
}
