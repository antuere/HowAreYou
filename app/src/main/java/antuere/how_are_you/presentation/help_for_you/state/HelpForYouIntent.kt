package antuere.how_are_you.presentation.help_for_you.state

sealed interface HelpForYouIntent {
    object HelplinesCardClicked : HelpForYouIntent
    object TelegramCardClicked : HelpForYouIntent
    object EmailCardClicked : HelpForYouIntent
}