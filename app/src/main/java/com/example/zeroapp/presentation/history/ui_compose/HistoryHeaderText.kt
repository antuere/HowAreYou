package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText
import timber.log.Timber

@Composable
fun HistoryHeaderText(
    modifier: Modifier = Modifier,
    rotation: () -> Float = { 0f },
    headerText: UiText
) {

    Timber.i("MVI error test : composed in header text rotate is $rotation")

    Text(
        text = headerText.asString(),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0))
            .graphicsLayer {
                rotationX = rotation()
            },
        textAlign = TextAlign.Center
    )
}
