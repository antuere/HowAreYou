package com.example.zeroapp.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import antuere.domain.dto.helplines.SupportedCountry
import com.example.zeroapp.R
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay


fun Context.findFragmentActivity(): FragmentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context as FragmentActivity
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun Modifier.paddingForBotAndTopBar() = composed(
    factory = {
        Modifier.padding(top = 64.dp, bottom = 80.dp)
    },

    inspectorInfo = debugInspectorInfo {
        name = "paddingForBotAndTopBar"
    }
)

fun Modifier.paddingTopBar() = composed(
    factory = {
        Modifier.padding(top = 64.dp)
    },

    inspectorInfo = debugInspectorInfo {
        name = "paddingTopBar"
    }
)


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
            scaleX = scale
            scaleY = scale
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
    }
)

fun Modifier.animateScaleOnce() = composed(
    factory = {
        var isAnimated by remember {
            mutableStateOf(true)
        }

        val scale by animateFloatAsState(
            targetValue = if (isAnimated) 1.2f else 1f,
            animationSpec = repeatable(
                iterations = 2,
                animation = tween(durationMillis = 100),
                repeatMode = RepeatMode.Reverse
            ),
        )

//        val transitionY by animateFloatAsState(
//            targetValue = if (isAnimated) 15f else 0f,
//            animationSpec = repeatable(
//                iterations = 1,
//                animation = tween(durationMillis = 100),
//                repeatMode = RepeatMode.Reverse
//            ),
//        )
        LaunchedEffect(key1 = isAnimated) {
            delay(200)
            isAnimated = false
        }

        Modifier.graphicsLayer {
//            translationY = if (isAnimated) -transitionY else 0f
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
        var isAnimated by remember {
            mutableStateOf(true)
        }

        val scale by animateFloatAsState(
            targetValue = if (isAnimated) 0.8f else 1f,
            animationSpec = repeatable(
                iterations = 2,
                animation = tween(durationMillis = 100),
                repeatMode = RepeatMode.Reverse
            ),
        )

        LaunchedEffect(key1 = isAnimated) {
            delay(175)
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
        var isAnimated by remember {
            mutableStateOf(true)
        }

        val scale by animateFloatAsState(
            targetValue = if (isAnimated) 1.2f else 1f,
            animationSpec = repeatable(
                iterations = 2,
                animation = tween(durationMillis = 100),
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

@Composable
fun SupportedCountry.getName(): String {
    return when (this) {
        is SupportedCountry.Italy -> stringResource(id = R.string.italy)
        is SupportedCountry.Russia -> stringResource(id = R.string.russia)
        is SupportedCountry.USA -> stringResource(id = R.string.usa)
    }
}

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