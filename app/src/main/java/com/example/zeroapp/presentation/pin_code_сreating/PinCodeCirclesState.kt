package com.example.zeroapp.presentation.pin_code_сreating

sealed class PinCodeCirclesState {
    object IsShowNone : PinCodeCirclesState()
    object IsShowFirst : PinCodeCirclesState()
    object IsShowSecond : PinCodeCirclesState()
    object IsShowThird : PinCodeCirclesState()
    object IsShowFourth : PinCodeCirclesState()
    object IsShowAll : PinCodeCirclesState()
}