package antuere.how_are_you.presentation.screens.history.state

import antuere.domain.dto.ToggleBtnState
import antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker.SelectedDates

sealed interface FilterState {
    data class Activated(val selectedDates: SelectedDates) : FilterState
    data class Disabled(val toggleBtnState: ToggleBtnState) : FilterState
}