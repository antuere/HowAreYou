package antuere.how_are_you.presentation.screens.history.ui_compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.ShadowLine

@Composable
fun HistoryHeaderText(
    modifier: Modifier = Modifier,
    rotation: () -> Float = { 0f },
    headerText: String,
    isShowShadow: () -> Boolean = { false },
) {
    Column {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
        Text(
            text = headerText,
            modifier = modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationX = rotation()
                },
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
        )

        ShadowLine(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.spacer_height_2))
                .fillMaxWidth(),
            isShowShadow = isShowShadow
        )
    }
}
