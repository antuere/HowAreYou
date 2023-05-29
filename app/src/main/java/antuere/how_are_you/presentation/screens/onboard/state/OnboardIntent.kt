package antuere.how_are_you.presentation.screens.onboard.state

sealed interface OnboardIntent {
    object EnterInAppClicked : OnboardIntent
    object ScipOnboardClicked : OnboardIntent
}