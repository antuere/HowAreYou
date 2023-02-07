package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.DarkGreen
import antuere.how_are_you.presentation.pin_code_creation.PinCirclesState
import antuere.how_are_you.util.extensions.animateScaleDownOnce
import antuere.how_are_you.util.extensions.animateScaleOnce
import antuere.how_are_you.util.extensions.animateScaleUpOnce
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
                ProgressCircle(
                    drawId = R.drawable.ic_circle_filled,
                    modifier = Modifier.animateScaleOnce()
                )
                repeat(3) {
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                }
            }
            PinCirclesState.SECOND -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(
                    drawId = R.drawable.ic_circle_filled,
                    modifier = Modifier.animateScaleOnce()
                )
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
            }
            PinCirclesState.THIRD -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(
                    drawId = R.drawable.ic_circle_filled,
                    modifier = Modifier.animateScaleOnce()
                )
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
            }
            PinCirclesState.FOURTH -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(
                    drawId = R.drawable.ic_circle_filled,
                    modifier = Modifier.animateScaleOnce()
                )
            }
            PinCirclesState.WRONG_PIN -> {
                repeat(4) {
                    ProgressCircle(
                        modifier = Modifier.shake(),
                        colorFilter = ColorFilter.tint(Color.Red),
                        alpha = 0.75F,
                        drawId = R.drawable.ic_circle_filled
                    )
                }
            }

            PinCirclesState.CORRECT_PIN -> {
                repeat(4) {
                    ProgressCircle(
                        drawId = R.drawable.ic_circle_filled,
                        colorFilter = ColorFilter.tint(DarkGreen),
                        modifier = Modifier.animateScaleUpOnce()
                    )
                }
            }

        }
    }
}