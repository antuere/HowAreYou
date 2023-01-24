package com.example.zeroapp.presentation.pin_code_creation.state

sealed interface PinCodeCreationSideEffect {
    object PinCreated : PinCodeCreationSideEffect
}