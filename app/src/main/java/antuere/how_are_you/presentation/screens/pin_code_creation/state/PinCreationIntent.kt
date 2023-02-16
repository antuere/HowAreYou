package antuere.how_are_you.presentation.screens.pin_code_creation.state

sealed interface PinCreationIntent {
    data class NumberClicked(val number: String) : PinCreationIntent
    object PinStateReset : PinCreationIntent
}