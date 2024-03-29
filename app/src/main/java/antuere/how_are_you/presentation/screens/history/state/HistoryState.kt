package antuere.how_are_you.presentation.screens.history.state

import androidx.annotation.DrawableRes
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed interface HistoryState {

    data class LoadingShimmer(
        val cellsAmount: Int = 3,
        val aspectRatioForItem: Float = 1F,
        val toggleBtnState: ToggleBtnState = ToggleBtnState.CURRENT_MONTH,
        val dateTextPlug: UiText = UiText.StringResource(R.string.loading),
        val toggleButtons: Map<UiText, ToggleBtnState> = toggleButtonsList,
    ) : HistoryState

    sealed interface Empty : HistoryState {
        data class NoEntriesYet(val message: UiText) : Empty

        data class FromToggleGroup(
            val message: UiText,
            @DrawableRes val calendarImageRes: Int,
            val toggleBtnState: ToggleBtnState,
            val toggleButtons: Map<UiText, ToggleBtnState> = toggleButtonsList,
        ) : Empty

        class FromFilter(val message: UiText) : Empty
    }

    sealed class Loaded : HistoryState {

        data class Default(
            val dayList: List<Day>,
            val toggleBtnState: ToggleBtnState,
            val cellsAmountForGrid: Int,
            val textHeadline: UiText,
            val toggleButtons: Map<UiText, ToggleBtnState> = toggleButtonsList,
        ) : Loaded()

        data class FilterSelected(
            val dayList: List<Day>,
            val cellsAmountForGrid: Int = 3,
            val textHeadline: UiText,
        ) : Loaded()
    }
}

private val allDaysBtnName = UiText.StringResource(R.string.all_days_title)
private val weekBtnName = UiText.StringResource(R.string.last_week_title)
private val monthBtnName = UiText.StringResource(R.string.current_month_title)

val toggleButtonsList: Map<UiText, ToggleBtnState> = mapOf(
    weekBtnName to ToggleBtnState.LAST_WEEK,
    monthBtnName to ToggleBtnState.CURRENT_MONTH,
    allDaysBtnName to ToggleBtnState.ALL_DAYS,
)
