package antuere.how_are_you.presentation.screens.home.state

sealed interface HomeIntent {
    object FabClicked : HomeIntent
    object MentalTipsClicked : HomeIntent
    object HelpForYouClicked : HomeIntent
    object CatsClicked : HomeIntent
    object FavoritesClicked : HomeIntent
}