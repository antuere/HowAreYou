package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ProgressCircle(
    @DrawableRes drawId: Int,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    alpha: Float = 1F,
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

@Composable
fun ProgressCircleAnimated(
    @DrawableRes drawId: Int,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    alpha: Float = 1F,
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = keyframes {
                durationMillis = 300
                1.1f at 100
                1.2f at 200
                1f at 300
            }
        )

    }
    Image(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .scale(scale.value),
        painter = painterResource(
            id = drawId
        ),
        alpha = alpha,
        contentDescription = null,
        colorFilter = colorFilter
    )
}