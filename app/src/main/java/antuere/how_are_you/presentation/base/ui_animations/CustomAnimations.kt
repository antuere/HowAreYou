package antuere.how_are_you.presentation.base.ui_animations

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
fun materialSlideIn(
    forward: Boolean,
    duration: Int = Constants.ANIM_DEFAULT_DURATION,
): EnterTransition {
    return slideInHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
        if (forward) fullWidth / 3 else -fullWidth / 3
    } + materialFadeThroughIn()
}


@ExperimentalAnimationApi
fun materialSlideOut(
    forward: Boolean,
    duration: Int = Constants.ANIM_DEFAULT_DURATION,
): ExitTransition {
    return slideOutHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
        if (forward) fullWidth / 3 else -fullWidth / 3
    } + materialFadeThroughOut()
}
