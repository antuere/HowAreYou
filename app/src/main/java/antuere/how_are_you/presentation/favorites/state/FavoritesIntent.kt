package antuere.how_are_you.presentation.favorites.state

import antuere.domain.dto.Day

sealed interface FavoritesIntent {
    data class DayClicked(val day: Day) : FavoritesIntent
    data class DayLongClicked(val day: Day) : FavoritesIntent
}