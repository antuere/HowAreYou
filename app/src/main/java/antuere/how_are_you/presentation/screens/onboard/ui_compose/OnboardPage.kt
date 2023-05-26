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
        title = UiText.DefaultString("First page"),
        description = UiText.DefaultString("Thanks for taetasbhdkasnkj askdnakjd graeat app")
    )

    object Days : OnboardPage(
        imageRes = R.drawable.onboard_days,
        title = UiText.DefaultString("Second page"),
        description = UiText.DefaultString("Wowo regar2 123 sds kjkjsdkas")
    )

    object Cats : OnboardPage(
        imageRes = R.drawable.onboard_cat,
        title = UiText.DefaultString("Third page"),
        description = UiText.DefaultString("Yeah nice app can used adjashdajsd yyyyyyeeeeeeeeee")
    )

    object MentalTips : OnboardPage(
        imageRes = R.drawable.onboard_tips,
        title = UiText.DefaultString("Third page"),
        description = UiText.DefaultString("Yeah nice app can used adjashdajsd yyyyyyeeeeeeeeee")
    )

    object Helplines : OnboardPage(
        imageRes = R.drawable.onboard_helplines,
        title = UiText.DefaultString("Third page"),
        description = UiText.DefaultString("Yeah nice app can used adjashdajsd yyyyyyeeeeeeeeee")
    )
}
