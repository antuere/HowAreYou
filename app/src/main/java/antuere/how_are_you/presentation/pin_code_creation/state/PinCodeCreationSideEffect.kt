package antuere.how_are_you.presentation.pin_code_creation.state

sealed interface PinCodeCreationSideEffect {
    object PinCreated : PinCodeCreationSideEffect
}