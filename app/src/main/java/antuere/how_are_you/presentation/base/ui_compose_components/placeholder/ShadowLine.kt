package antuere.how_are_you.presentation.base.ui_compose_components.placeholder

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults

@Composable
fun ShadowLine(
    modifier: Modifier = Modifier,
    isShowShadow: () -> Boolean = { true },
    nonShadowLineHeight: Dp = dimensionResource(id = R.dimen.spacer_height_2),
) {
    if (isShowShadow()) {
        Canvas(modifier = modifier) {
            drawRect(brush = GradientDefaults.shadow())
        }
    } else {
        Spacer(modifier = Modifier.height(nonShadowLineHeight))
    }
}