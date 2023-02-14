package antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Float = 6f,
    orientation: LinearProgressBarOrientation = LinearProgressBarOrientation.HORIZONTAL,
) {
    when (orientation) {
        LinearProgressBarOrientation.VERTICAL -> {
            Canvas(
                modifier = modifier
                    .progressSemantics(progress)
                    .size(4.dp, 240.dp)
            ) {
                drawVerticalBar(progress, color, strokeWidth)
            }
        }
        LinearProgressBarOrientation.HORIZONTAL -> {
            Canvas(
                modifier = modifier
                    .progressSemantics(progress)
                    .size(240.dp, 4.dp)
            ) {
                drawHorizontalBar(progress, color, strokeWidth)
            }
        }
    }

}

@Composable
fun LinearProgressBarWrapper(
    modifier: Modifier = Modifier,
    orientation: LinearProgressBarOrientation = LinearProgressBarOrientation.HORIZONTAL,
    scale: () -> Float,
) {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(600)
    )
    progress = (1 - scale()) * 25

    LinearProgressBar(
        progress = animatedProgress,
        modifier = modifier,
        orientation = orientation
    )
}

private fun DrawScope.drawHorizontalBar(
    progress: Float,
    color: Color,
    strokeWidth: Float,
) {
    val width = size.width
    val height = size.height
    val yOffset = height / 2

    val barStart = width / 2
    val barEndForRight = width / 2 + (progress * width / 2)
    val barEndForLeft = width / 2 - (progress * width / 2)

    // Progress line draw to right
    drawLine(
        color = color,
        start = Offset(barStart, yOffset),
        end = Offset(barEndForRight, yOffset),
        strokeWidth = strokeWidth
    )

    // Progress line draw to left
    drawLine(
        color = color,
        start = Offset(barStart, yOffset),
        end = Offset(barEndForLeft, yOffset),
        strokeWidth = strokeWidth
    )
}

private fun DrawScope.drawVerticalBar(
    progress: Float,
    color: Color,
    strokeWidth: Float,
) {
    val width = size.width
    val height = size.height
    val xOffset = width / 2

    val barStart = height / 2
    val barEndForTop = height / 2 + (progress * height / 2)
    val barEndForBot = height / 2 - (progress * height / 2)

    // Progress line draw to top
    drawLine(
        color = color,
        start = Offset(xOffset, barStart),
        end = Offset(xOffset, barEndForTop),
        strokeWidth = strokeWidth
    )

    // Progress line draw to bot
    drawLine(
        color = color,
        start = Offset(xOffset, barStart),
        end = Offset(xOffset, barEndForBot),
        strokeWidth = strokeWidth
    )
}

enum class LinearProgressBarOrientation {
    VERTICAL, HORIZONTAL
}
