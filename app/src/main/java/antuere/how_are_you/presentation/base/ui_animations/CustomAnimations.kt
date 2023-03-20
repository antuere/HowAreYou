package antuere.how_are_you.presentation.base.ui_animations

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import antuere.domain.util.Constants


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
    return scaleIn(
        initialScale = 0.93F,
        animationSpec = tween(duration)
    ) + fadeIn(animationSpec = tween(duration / 2))
}

@ExperimentalAnimationApi
fun materialFadeThroughOut(duration: Int = Constants.ANIM_DEFAULT_DURATION): ExitTransition {
    return scaleOut(
        targetScale = 0.93F,
        animationSpec = tween(duration)
    ) + fadeOut(animationSpec = tween(duration / 2))
}

@ExperimentalAnimationApi
fun materialSlideIn(forward: Boolean, duration: Int = Constants.ANIM_DEFAULT_DURATION): EnterTransition {
    return if (forward) {
        slideInHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
            fullWidth / 3
        } + materialFadeThroughIn()
    } else {
        slideInHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
            -fullWidth / 3
        } + materialFadeThroughIn()
    }
}

@ExperimentalAnimationApi
fun materialSlideOut(forward: Boolean, duration: Int = Constants.ANIM_DEFAULT_DURATION): ExitTransition {
    return if (forward) {
        slideOutHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
            fullWidth / 3
        } + materialFadeThroughOut()
    } else {
        slideOutHorizontally(animationSpec = tween(duration)) { fullWidth: Int ->
            -fullWidth / 3
        } + materialFadeThroughOut()
    }
}