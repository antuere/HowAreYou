package com.example.zeroapp.presentation.loginWithEmail

import androidx.annotation.StringRes

sealed class LoginState {
    object Successful : LoginState()

    data class EmptyFields(@StringRes val res: Int) : LoginState()

    data class ErrorFromFireBase(var message: String) : LoginState()
}
