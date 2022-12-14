package com.example.zeroapp.presentation.reset_password

import androidx.annotation.StringRes


sealed class ResetPasswordState {

    data class Successful(@StringRes val  res: Int) : ResetPasswordState()

    data class EmptyFields(@StringRes val res: Int) : ResetPasswordState()

    data class ErrorFromFireBase(var message: String) : ResetPasswordState()
}