package com.example.zeroapp.presentation.sign_in_with_email

import androidx.annotation.StringRes

sealed class SignInState {
    object Successful : SignInState()

    data class EmptyFields(@StringRes val res: Int) : SignInState()

    data class ErrorFromFireBase(var message: String) : SignInState()
}
