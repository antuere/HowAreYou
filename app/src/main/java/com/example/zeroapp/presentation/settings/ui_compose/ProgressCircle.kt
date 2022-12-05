package com.example.zeroapp.presentation.settings.ui_compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.zeroapp.R
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCirclesState

@Composable
fun ProgressCircle(@DrawableRes drawId: Int) {
    val paddingHorizontal = 2.dp

    Image(
        modifier = Modifier.padding(horizontal = paddingHorizontal),
        painter = painterResource(
            id = drawId
        ),
        contentDescription = null
    )
}