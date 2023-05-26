package antuere.how_are_you.presentation.screens.onboard.state

import antuere.how_are_you.presentation.screens.onboard.ui_compose.OnboardPage

data class OnboardState(
    val pages: List<OnboardPage> = listOf(
        OnboardPage.Welcome,
        OnboardPage.Days,
        OnboardPage.Cats,
        OnboardPage.Helplines,
        OnboardPage.MentalTips,
    ),
)
