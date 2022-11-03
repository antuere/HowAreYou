package com.example.zeroapp.presentation.summary

sealed class AuthenticationState {
    object Successful : AuthenticationState()
    object Error : AuthenticationState()
    object NotRequired : AuthenticationState()
}