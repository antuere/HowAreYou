package antuere.how_are_you.presentation.screens.secure_entry.state

import antuere.how_are_you.presentation.screens.pin_code_creation.PinCirclesState


data class SecureEntryState(
    val pinCirclesState: PinCirclesState = PinCirclesState.NONE,
    val isShowBiometricBtn: Boolean = false
)
