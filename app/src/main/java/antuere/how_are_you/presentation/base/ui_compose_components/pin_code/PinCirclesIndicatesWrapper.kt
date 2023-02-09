package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.compose.runtime.Composable
import antuere.how_are_you.presentation.pin_code_creation.PinCirclesState

@Composable
fun PinCirclesIndicatesWrapper(
    pinCirclesState: () -> PinCirclesState,
) {
    PinCirclesIndicates(pinCodeCirclesState = pinCirclesState())
}