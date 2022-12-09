package com.example.zeroapp.presentation.base.ui_compose_components.pin_code

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.example.zeroapp.R
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCirclesState
import com.example.zeroapp.presentation.settings.ui_compose.ProgressCircle

@Composable
fun PinCirclesIndicates(pinCodeCirclesState: PinCodeCirclesState) {
    Row {
        when (pinCodeCirclesState) {
            PinCodeCirclesState.NONE -> {
                repeat(4) {
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                }
            }
            PinCodeCirclesState.FIRST -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                repeat(3) {
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                }
            }
            PinCodeCirclesState.SECOND -> {
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_filled)
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
            }
            PinCodeCirclesState.THIRD -> {
                repeat(3) {
                    ProgressCircle(drawId = R.drawable.ic_circle_filled)
                }
                ProgressCircle(drawId = R.drawable.ic_circle_outlined)
            }
            PinCodeCirclesState.FOURTH, PinCodeCirclesState.ALL -> {
                repeat(4) {
                    ProgressCircle(drawId = R.drawable.ic_circle_filled)
                }
            }

        }
    }
}