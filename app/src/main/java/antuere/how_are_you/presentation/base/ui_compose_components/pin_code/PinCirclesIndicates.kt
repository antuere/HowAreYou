package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.color_dark_green
import antuere.how_are_you.presentation.base.ui_theme.color_red
import antuere.how_are_you.presentation.screens.pin_code_creation.PinCirclesState
import antuere.how_are_you.util.extensions.animateScaleDownOnce
import antuere.how_are_you.util.extensions.shake

@Composable
fun PinCirclesIndicates(pinCodeCirclesState: PinCirclesState) {
    Row {
        when (pinCodeCirclesState) {
            PinCirclesState.NONE -> {
                repeat(4) {
                    ProgressCircle(
                        drawId = R.drawable.ic_circle_outlined,
                        modifier = Modifier.animateScaleDownOnce()
                    )
                }
            }
            PinCirclesState.FIRST -> {
                ProgressCircleAnimated(drawId = R.drawable.ic_circle_filled)
                repeat(3) {
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                }
            }
            PinCirclesState.SECOND -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircleAnimated(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
            }
            PinCirclesState.THIRD -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircleAnimated(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
            }
            PinCirclesState.FOURTH -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircleAnimated(drawId = R.drawable.ic_circle_filled)
            }
            PinCirclesState.WRONG_PIN -> {
                repeat(4) {
                    ProgressCircle(
                        modifier = Modifier.shake(),
                        colorFilter = ColorFilter.tint(color_red),
                        drawId = R.drawable.ic_circle_filled
                    )
                }
            }

            PinCirclesState.CORRECT_PIN -> {
                repeat(4) {
                    ProgressCircleAnimated(
                        drawId = R.drawable.ic_circle_filled,
                        colorFilter = ColorFilter.tint(color_dark_green),
                    )
                }
            }
        }
    }
}