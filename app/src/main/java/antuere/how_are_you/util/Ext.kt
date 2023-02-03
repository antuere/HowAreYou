package antuere.how_are_you.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import antuere.domain.dto.helplines.SupportedCountry
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


fun Context.findFragmentActivity(): FragmentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context as FragmentActivity
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun String.upperCaseFirstCharacter(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun Modifier.paddingBotAndTopBar(): Modifier {
    return padding(top = 64.dp, bottom = 80.dp)
}

fun Modifier.paddingTopBar(): Modifier {
    return padding(top = 64.dp)
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

fun Modifier.animateMoving() = composed(
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

        Modifier.rotate(rotation).scale(scale)
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateMoving"
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

fun Modifier.shimmer(
    duration: Int,
): Modifier = composed {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = createCustomTheme(duration),
    )
    shimmer(customShimmer = shimmer)
}

fun SupportedCountry.getName(): UiText {
    return when (this) {
        is SupportedCountry.Italy -> UiText.StringResource(R.string.italy)
        is SupportedCountry.Russia -> UiText.StringResource(R.string.russia)
        is SupportedCountry.USA -> UiText.StringResource(R.string.usa)
        is SupportedCountry.France -> UiText.StringResource(R.string.france)
        is SupportedCountry.China -> UiText.StringResource(R.string.china)
    }
}

fun LazyListState.animateScrollAndCentralize(index: Int, scope: CoroutineScope) {
    val itemInfo = this.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    scope.launch {
        if (itemInfo != null) {
            val center = this@animateScrollAndCentralize.layoutInfo.viewportEndOffset / 2
            val childCenter = itemInfo.offset + itemInfo.size / 2
            this@animateScrollAndCentralize.animateScrollBy((childCenter - center).toFloat())
        } else {
            this@animateScrollAndCentralize.animateScrollToItem(index)
        }
    }
}

@Composable
fun ((Boolean) -> Unit).toStable() = remember { this }

@JvmName("toStableStringUnit")
@Composable
fun ((String) -> Unit).toStable() = remember { this }

@Composable
fun (() -> Unit).toStable() = remember { this }

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