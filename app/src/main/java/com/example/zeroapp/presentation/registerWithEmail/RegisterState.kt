package com.example.zeroapp.presentation.registerWithEmail

import androidx.annotation.StringRes

sealed class RegisterState {

    object Successful : RegisterState()

    data class EmptyFields(@StringRes val res: Int) : RegisterState()

    data class PasswordsError(@StringRes val res: Int) : RegisterState()

    data class ErrorFromFireBase(var message: String) : RegisterState()
}
