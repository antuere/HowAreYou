package antuere.how_are_you.presentation.screens.history.state

import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker.SelectedDates

sealed interface HistoryIntent {
    data class DayClicked(val day: Day) : HistoryIntent
    data class DayLongClicked(val day: Day) : HistoryIntent
    data class ToggleBtnChanged(val toggleBtnState: ToggleBtnState) : HistoryIntent
    data class DaysInFilterSelected(val selectedDates: SelectedDates) : HistoryIntent
    object FilterCloseBtnClicked : HistoryIntent
}