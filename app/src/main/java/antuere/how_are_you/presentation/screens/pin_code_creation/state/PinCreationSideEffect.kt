package antuere.how_are_you.presentation.screens.pin_code_creation.state

sealed interface PinCreationSideEffect {
    object PinCreated : PinCreationSideEffect
}