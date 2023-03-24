package antuere.how_are_you.presentation.screens.history.state

import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import java.time.LocalDate

sealed interface HistoryIntent {
    data class DayClicked(val day: Day) : HistoryIntent
    data class DayLongClicked(val day: Day) : HistoryIntent
    data class ToggleBtnChanged(val toggleBtnState: ToggleBtnState) : HistoryIntent
    data class DaysInFilterSelected(val startDate: LocalDate, val endDate: LocalDate) :
        HistoryIntent
    object FilterBtnClicked : HistoryIntent
    object FilterCloseBtnClicked : HistoryIntent
    object FilterSheetClosed : HistoryIntent
}