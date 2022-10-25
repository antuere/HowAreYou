package com.example.zeroapp.presentation.login_with_email

import androidx.annotation.StringRes

sealed class LoginState {
    object Successful : LoginState()

    data class EmptyFields(@StringRes val res: Int) : LoginState()

    data class ErrorFromFireBase(var message: String) : LoginState()
}
