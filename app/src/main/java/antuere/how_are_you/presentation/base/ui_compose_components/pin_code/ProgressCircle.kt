package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProgressCircle(
    @DrawableRes drawId: Int,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    alpha : Float = 1F
) {
    Image(
        modifier = modifier.padding(horizontal = 2.dp),
        painter = painterResource(
            id = drawId
        ),
        alpha = alpha,
        contentDescription = null,
        colorFilter = colorFilter
    )
}