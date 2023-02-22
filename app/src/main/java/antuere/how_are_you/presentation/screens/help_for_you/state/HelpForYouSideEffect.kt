package antuere.how_are_you.presentation.screens.help_for_you.state

sealed interface HelpForYouSideEffect {
    object NavigateToHelplines : HelpForYouSideEffect
    object NavigateToEmailClient : HelpForYouSideEffect
}