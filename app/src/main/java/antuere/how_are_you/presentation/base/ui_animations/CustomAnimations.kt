package antuere.how_are_you.presentation.base.ui_animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import antuere.domain.util.Constants


private val Int.outgoingDuration: Int
    get() = (this * 0.35f).toInt()

private val Int.incomingDuration: Int
    get() = this - this.outgoingDuration

@ExperimentalAnimationApi
fun materialFadeIn(duration: Int = 150): EnterTransition {
    return fadeIn(animationSpec = tween(delayMillis = duration, easing = LinearEasing)) +
            scaleIn(
                initialScale = 0.5F,
                animationSpec = tween(duration)
            )
}

@ExperimentalAnimationApi
fun materialFadeOut(duration: Int = 50): ExitTransition {
    return fadeOut(animationSpec = tween(delayMillis = duration, easing = LinearEasing))
}

@ExperimentalAnimationApi
fun materialFadeThroughIn(duration: Int = Constants.ANIM_DEFAULT_DURATION): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            durationMillis = duration.incomingDuration,
            delayMillis = duration.outgoingDuration,
            easing = LinearOutSlowInEasing
        )
    ) + scaleIn(
        initialScale = 0.93F,
        animationSpec = tween(
            durationMillis = duration.incomingDuration,
            delayMillis = duration.outgoingDuration,
            easing = LinearOutSlowInEasing
        )
    )
}

@ExperimentalAnimationApi
fun materialFadeThroughOut(duration: Int = Constants.ANIM_DEFAULT_DURATION): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            durationMillis = duration.outgoingDuration,
            delayMillis = 0,
            easing = FastOutLinearInEasing
        )
    )
}

@ExperimentalAnimationApi
fun materialSharedAxisXIn(
    forward: Boolean,
    duration: Int = Constants.ANIM_DEFAULT_DURATION,
): EnterTransition {
    return slideInHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
        if (forward) fullWidth / 3 else -fullWidth / 3
    } + fadeIn(
        animationSpec = tween(
            durationMillis = duration.incomingDuration,
            delayMillis = duration.outgoingDuration,
            easing = LinearOutSlowInEasing
        )
    )
}


@ExperimentalAnimationApi
fun materialSharedAxisXOut(
    forward: Boolean,
    duration: Int = Constants.ANIM_DEFAULT_DURATION,
): ExitTransition {
    return slideOutHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
        if (forward) -fullWidth / 3 else fullWidth / 3
    } + fadeOut(
        animationSpec = tween(
            durationMillis = duration.outgoingDuration,
            delayMillis = 0,
            easing = FastOutLinearInEasing
        )
    )
}

@ExperimentalAnimationApi
fun materialSharedAxisZIn(
    forward: Boolean,
    durationMillis: Int = Constants.ANIM_DEFAULT_DURATION,
): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis.incomingDuration,
        delayMillis = durationMillis.outgoingDuration,
        easing = LinearOutSlowInEasing
    )
) + scaleIn(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    initialScale = if (forward) 0.85f else 1.1f
)

@ExperimentalAnimationApi
fun materialSharedAxisZOut(
    forward: Boolean,
    durationMillis: Int = Constants.ANIM_DEFAULT_DURATION,
): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis.outgoingDuration,
        delayMillis = 0,
        easing = FastOutLinearInEasing
    )
) + scaleOut(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    ),
    targetScale = if (forward) 1.1f else 0.85f
)
