package com.example.zeroapp.presentation.history

sealed class ToggleButtonState {

    data class Filter(val pair: Pair<Long,Long>) : ToggleButtonState()

    object AllDays : ToggleButtonState()

    object LastWeek : ToggleButtonState()

    object CurrentMonth : ToggleButtonState()
}
