package antuere.how_are_you.util.extensions

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import antuere.how_are_you.util.dpToPixel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


fun Modifier.paddingBotAndTopBar(): Modifier {
    return padding(top = 64.dp, bottom = 80.dp)
}

fun Modifier.paddingTopBar(): Modifier {
    return padding(top = 64.dp)
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.animatedPagerItem(pagerState: PagerState, page: Int): Modifier {
    return graphicsLayer {
        val pageOffset = (
                (pagerState.currentPage - page) + pagerState
                    .currentPageOffsetFraction
                ).absoluteValue
        lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }

        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }
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


fun Modifier.progressBox(
    progress: Float,
    color: Color,
    strokeWidth: Float = 5f,
): Modifier {
    return drawWithCache {
        val width = size.width
        val height = size.height

        val xStartPoint = width / 2
        val yStartPoint = height / 2

        val barEndForRight = xStartPoint + (progress * width / 2)
        val barEndForLeft = xStartPoint - (progress * width / 2)
        val barEndForTop = yStartPoint + (progress * height / 2)
        val barEndForBot = yStartPoint - (progress * height / 2)

        onDrawWithContent {
            drawContent()

            // TOP Progress line draw to right
            drawLine(
                color = color,
                start = Offset(xStartPoint, 0f),
                end = Offset(barEndForRight, 0f),
                strokeWidth = strokeWidth
            )

            // TOP Progress line draw to left
            drawLine(
                color = color,
                start = Offset(xStartPoint, 0f),
                end = Offset(barEndForLeft, 0f),
                strokeWidth = strokeWidth
            )

            // LEFT Progress line draw to top
            drawLine(
                color = color,
                start = Offset(0f, yStartPoint),
                end = Offset(0f, barEndForTop),
                strokeWidth = strokeWidth
            )

            // LEFT Progress line draw to bot
            drawLine(
                color = color,
                start = Offset(0f, yStartPoint),
                end = Offset(0f, barEndForBot),
                strokeWidth = strokeWidth
            )

            // BOT Progress line draw to right
            drawLine(
                color = color,
                start = Offset(xStartPoint, size.height),
                end = Offset(barEndForRight, size.height),
                strokeWidth = strokeWidth
            )

            // BOT Progress line draw to left
            drawLine(
                color = color,
                start = Offset(xStartPoint, size.height),
                end = Offset(barEndForLeft, size.height),
                strokeWidth = strokeWidth
            )

            // RIGHT Progress line draw to top
            drawLine(
                color = color,
                start = Offset(size.width, yStartPoint),
                end = Offset(size.width, barEndForTop),
                strokeWidth = strokeWidth
            )

            // RIGHT Progress line draw to bot
            drawLine(
                color = color,
                start = Offset(size.width, yStartPoint),
                end = Offset(size.width, barEndForBot),
                strokeWidth = strokeWidth
            )
        }
    }
        .progressSemantics(progress)
}

@OptIn(ExperimentalTextApi::class)
fun Modifier.borderWithText(
    text: String,
    textStyle: TextStyle,
    color: Color,
    strokeWidth: Int = 1,
) = composed(
    factory = {
        val textMeasurer = rememberTextMeasurer()
        val context = LocalContext.current

        drawWithCache {
            val textSize = textMeasurer.measure(text, textStyle).size
            val endTextPoint = dpToPixel(20, context) + textSize.width
            val path = Path().apply {
                moveTo(dpToPixel(12, context), 0f)
                lineTo(0f, 0f)
                lineTo(0f, size.height)
                lineTo(size.width, size.height)
                lineTo(size.width, 0f)
                lineTo(endTextPoint, 0f)
            }
            val stoke = Stroke(
                width = dpToPixel(strokeWidth, context),
                pathEffect = PathEffect.cornerPathEffect(dpToPixel(8, context))
            )
            val textOffset = Offset(x = dpToPixel(16, context), y = -textSize.height / 1.7f)

            onDrawWithContent {
                drawContent()

                drawPath(
                    path = path,
                    color = color,
                    style = stoke
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = text,
                    style = textStyle,
                    topLeft = textOffset
                )
            }
        }
            .padding(16.dp)
    },
    inspectorInfo = debugInspectorInfo {
        name = "borderWithText"
    }
)

fun Modifier.shake() = composed(
    factory = {
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 0.9f,
            targetValue = 1.2F,
            animationSpec = infiniteRepeatable(
                animation = tween(240),
                repeatMode = RepeatMode.Reverse
            )
        )

        graphicsLayer {
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
            .graphicsLayer {
                rotationZ = rotation
                scaleX = scale
                scaleY = scale
            }
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateMoving"
    }
)


fun Modifier.animateScaleDownOnce() = composed(
    factory = {
        val scale = remember { Animatable(1f) }

        LaunchedEffect(Unit) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 240
                    0.9f at 80
                    0.8f at 160
                    1f at 240
                }
            )
        }

        Modifier.scale(scale.value)
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateScaleDownOnce"
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