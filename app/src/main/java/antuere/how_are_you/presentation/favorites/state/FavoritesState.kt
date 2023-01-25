package antuere.how_are_you.presentation.favorites.state

import antuere.domain.dto.Day
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface FavoritesState {

    data class LoadingShimmer(
        val cellsAmount: Int = 3,
        val aspectRatioForItem: Float = 1F
    ) : FavoritesState

    data class Empty(val message: UiText = UiText.StringResource(R.string.favorites_hint)) :
        FavoritesState

    data class Loaded(
        val dayList: List<Day>,
        val cellsAmountForGrid: Int = 3
    ) : FavoritesState

}