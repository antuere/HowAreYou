package antuere.how_are_you.util.extensions

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun Modifier.paddingBotAndTopBar(): Modifier {
    return padding(top = 64.dp, bottom = 80.dp)
}

fun Modifier.paddingTopBar(): Modifier {
    return padding(top = 64.dp)
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bringIntoViewForFocused(
    bringIntoViewRequester: BringIntoViewRequester,
    scope: CoroutineScope,
): Modifier {
    return onFocusEvent { event ->
        if (event.isFocused) {
            scope.launch {
                bringIntoViewRequester.bringIntoView()
            }
        }
    }
}

fun Modifier.shake() = composed(
    factory = {
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.9f,
            targetValue = 1.2F,
            animationSpec = infiniteRepeatable(
                animation = tween(250),
                repeatMode = RepeatMode.Reverse
            )
        )

        Modifier.graphicsLayer {
            this.translationX
            scaleX = scale
            scaleY = scale
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
    }
)

fun Modifier.animateRotation() = composed(
    factory = {
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.8F,
            targetValue = 1.5F,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            )
        )
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            )
        )

        Modifier
            .rotate(rotation)
            .scale(scale)
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateMoving"
    }
)

fun Modifier.animateScaleOnce() = composed(
    factory = {
        var isAnimated by remember { mutableStateOf(true) }
        val scale by animateFloatAsState(
            targetValue = if (isAnimated) 1.2f else 1f,
            animationSpec = repeatable(
                iterations = 1,
                animation = tween(durationMillis = 125),
                repeatMode = RepeatMode.Restart
            ),
        )

        LaunchedEffect(key1 = isAnimated) {
            delay(125)
            isAnimated = false
        }

        Modifier.graphicsLayer {
            scaleX = if (isAnimated) scale else 1f
            scaleY = if (isAnimated) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateScaleOnce"
    }
)

fun Modifier.animateScaleDownOnce() = composed(
    factory = {
        var isAnimated by remember { mutableStateOf(true) }
        val scale by animateFloatAsState(
            targetValue = if (isAnimated) 0.8f else 1f,
            animationSpec = repeatable(
                iterations = 1,
                animation = tween(durationMillis = 150),
                repeatMode = RepeatMode.Restart
            ),
        )

        LaunchedEffect(key1 = isAnimated) {
            delay(150)
            isAnimated = false
        }

        Modifier.graphicsLayer {
            scaleX = if (isAnimated) scale else 1f
            scaleY = if (isAnimated) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateScaleDownOnce"
    }
)

fun Modifier.animateScaleUpOnce() = composed(
    factory = {
        var isAnimated by remember { mutableStateOf(true) }
        val scale by animateFloatAsState(
            targetValue = if (isAnimated) 1.2f else 1f,
            animationSpec = repeatable(
                iterations = 1,
                animation = tween(durationMillis = 150),
                repeatMode = RepeatMode.Reverse
            ),
        )

        LaunchedEffect(key1 = isAnimated) {
            delay(150)
            isAnimated = false
        }

        Modifier.graphicsLayer {
            scaleX = if (isAnimated) scale else 1f
            scaleY = if (isAnimated) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateScaleUpOnce"
    }
)

fun Modifier.shimmer(
    duration: Int,
): Modifier = composed {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = createCustomTheme(duration),
    )
    shimmer(customShimmer = shimmer)
}

private fun createCustomTheme(duration: Int) = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = duration,
            delayMillis = 350
        ),
        repeatMode = RepeatMode.Restart,
    ),
    rotation = 5f,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 1.0f),
        Color.Unspecified.copy(alpha = 0.2f),
        Color.Unspecified.copy(alpha = 1.0f),
    ),
    shaderColorStops = null,
    shimmerWidth = 200.dp,
)