package com.example.zeroapp.presentation.history.state

import antuere.domain.dto.ToggleBtnState
import java.time.LocalDate

sealed interface FilterState {
    data class Activated(val firstDate: LocalDate, val secondDate: LocalDate) : FilterState
    data class Disabled(val toggleBtnState: ToggleBtnState) : FilterState
}