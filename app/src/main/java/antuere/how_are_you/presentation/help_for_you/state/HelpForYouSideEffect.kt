package antuere.how_are_you.presentation.help_for_you.state

sealed interface HelpForYouSideEffect {
    object NavigateToHelplines : HelpForYouSideEffect
}