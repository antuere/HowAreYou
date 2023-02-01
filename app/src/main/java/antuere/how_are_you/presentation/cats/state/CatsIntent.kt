package antuere.how_are_you.presentation.cats.state

sealed interface CatsIntent {
    object UpdateCatsClicked : CatsIntent
}