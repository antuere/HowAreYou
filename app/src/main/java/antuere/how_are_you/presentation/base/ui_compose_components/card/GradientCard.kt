package antuere.how_are_you.presentation.base.ui_compose_components.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientCardWithOnClick(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    onClick: () -> Unit = {},
    gradient: Brush = GradientDefaults.primary(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleCard by animateFloatAsState(if (isPressed) 0.99f else 1f)

    ElevatedCard(
        modifier = modifier.graphicsLayer {
            scaleX = scaleCard
            scaleY = scaleCard
        },
        onClick = onClick,
        shape = shape,
        colors = colors,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            content.invoke(this)
        }
    }
}


@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    gradient: Brush = GradientDefaults.primary(),
    content: @Composable ColumnScope.() -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
    ) {
        Column(
            modifier = contentModifier
                .background(gradient)
        ) {
            content.invoke(this)
        }
    }
}