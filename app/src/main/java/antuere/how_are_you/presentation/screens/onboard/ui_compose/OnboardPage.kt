package antuere.how_are_you.presentation.screens.onboard.ui_compose

import androidx.annotation.DrawableRes
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed class OnboardPage(
    @DrawableRes val imageRes: Int,
    val title: UiText,
    val description: UiText,
) {
    object Welcome : OnboardPage(
        imageRes = R.drawable.onboard_welcome,
        title = UiText.StringResource(R.string.onboard_welcome_title),
        description = UiText.StringResource(R.string.onboard_welcome_desc),
    )

    object Days : OnboardPage(
        imageRes = R.drawable.onboard_days,
        title = UiText.StringResource(R.string.onboard_days_title),
        description = UiText.StringResource(R.string.onboard_days_desc)
    )

    object Cats : OnboardPage(
        imageRes = R.drawable.onboard_cat,
        title = UiText.StringResource(R.string.onboard_cats_title),
        description = UiText.StringResource(R.string.onboard_cats_desc),
    )

    object MentalTips : OnboardPage(
        imageRes = R.drawable.onboard_tips,
        title = UiText.StringResource(R.string.onboard_tips_title),
        description = UiText.StringResource(R.string.onboard_tips_desc)
    )

    object Helplines : OnboardPage(
        imageRes = R.drawable.onboard_helplines,
        title = UiText.StringResource(R.string.onboard_helplines_title),
        description = UiText.StringResource(R.string.onboard_helplines_desc)
    )
}
