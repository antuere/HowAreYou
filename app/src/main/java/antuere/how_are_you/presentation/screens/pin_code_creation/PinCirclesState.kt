package antuere.how_are_you.presentation.screens.pin_code_creation

import androidx.compose.runtime.Stable

@Stable
enum class PinCirclesState {
    NONE, FIRST, SECOND, THIRD, FOURTH, WRONG_PIN, CORRECT_PIN
}