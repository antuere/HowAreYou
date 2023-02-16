package antuere.how_are_you.presentation.screens.home.state

import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface HomeState {
    object Loading : HomeState

    data class Loaded(
        val quoteText: String,
        val quoteAuthor: String,
        val wishText: UiText,
        val fabButtonState: FabButtonState,
    ) : HomeState
}