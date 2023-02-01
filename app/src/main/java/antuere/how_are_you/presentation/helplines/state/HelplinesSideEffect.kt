package antuere.how_are_you.presentation.helplines.state

sealed interface HelplinesSideEffect {
    data class NavigateToWebsite(val website: String) : HelplinesSideEffect
    data class NavigateToDialNumber(val phoneNumber: String) : HelplinesSideEffect
}