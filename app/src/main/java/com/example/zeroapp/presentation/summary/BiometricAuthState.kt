package com.example.zeroapp.presentation.summary

sealed class BiometricAuthState {
    object Successful : BiometricAuthState()
    object Error : BiometricAuthState()
}