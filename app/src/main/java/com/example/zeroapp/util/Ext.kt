package com.example.zeroapp.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.StringRes
import androidx.compose.animation.core.*
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
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

@Composable
fun SnackbarHostState.ShowSnackBar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    LaunchedEffect(this) {
        this@ShowSnackBar.showSnackbar(
            message = message,
            duration = duration
        )
    }
}

@Composable
fun SnackbarHostState.ShowSnackBarAdvance(
    @StringRes messageId: Int,
    delayValue: Long = 2000,
    hideSnackbarAfterDelay: () -> Unit
) {
    val message = stringResource(id = messageId)

    LaunchedEffect(this) {
        this@ShowSnackBarAdvance.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
        delay(delayValue)
        hideSnackbarAfterDelay()
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
            scaleX = scale
            scaleY = scale
        }
    },
    inspectorInfo = debugInspectorInfo {
        name = "shake"
    }
)
