package antuere.how_are_you.presentation.base.ui_compose_components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource

@Composable
fun IconButtonScaleable(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = LocalContentColor.current,
    @DrawableRes iconRes: Int,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)
    IconButton(
        modifier = modifier.graphicsLayer {
            scaleX = scaleBtn
            scaleY = scaleBtn
        },
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Icon(
            painter = painterResource(iconRes),
            //  TODO добавить описание всех картинок в проект
            contentDescription = null,
            tint = color
        )
    }
}
