package com.example.zeroapp.presentation.pin_code_сreating

sealed class PinCodeCirclesState {
    object IsShowOne : PinCodeCirclesState()
    object IsShowTwo : PinCodeCirclesState()
    object IsShowThree : PinCodeCirclesState()
    object IsShowFour : PinCodeCirclesState()
}