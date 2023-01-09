package com.example.zeroapp.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.delay


fun Context.findFragmentActivity(): FragmentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context as FragmentActivity
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
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
            scaleX = if(isAnimated) scale else 1f
            scaleY = if(isAnimated) scale else 1f
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
            scaleX = if(isAnimated) scale else 1f
            scaleY = if(isAnimated) scale else 1f
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
            delay(200)
            isAnimated = false
        }

        Modifier.graphicsLayer {
            scaleX = if(isAnimated) scale else 1f
            scaleY = if(isAnimated) scale else 1f
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "animateScaleUpOnce"
    }
)