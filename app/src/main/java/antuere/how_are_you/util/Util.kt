package antuere.how_are_you.util

import android.content.Context
import android.util.TypedValue
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults

@Composable
fun rememberDaysGradientCache(gradients: List<Brush> = GradientDefaults.gradientsForSmiles()): HashMap<Int, Brush> {
    return remember {
        hashMapOf(
            antuere.data.R.drawable.smile_sad to gradients[0],
            antuere.data.R.drawable.smile_none to gradients[1],
            antuere.data.R.drawable.smile_low to gradients[2],
            antuere.data.R.drawable.smile_happy to gradients[3],
            antuere.data.R.drawable.smile_very_happy to gradients[4],
        )
    }
}

@Composable
fun ComposableLifecycle(
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit,
) {
    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun isKeyboardVisible(): State<Boolean> {
    val isImeVisible = WindowInsets.isImeVisible
    return rememberUpdatedState(isImeVisible)
//    val keyboardState = remember { mutableStateOf(false) }
//    val view = LocalView.current
//    LaunchedEffect(view) {
//        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
//            keyboardState.value = insets.isVisible(WindowInsetsCompat.Type.ime())
//            insets
//        }
//    }
//    return keyboardState
}

fun dpToPixel(dp: Int, context: Context): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        metrics
    )
}


