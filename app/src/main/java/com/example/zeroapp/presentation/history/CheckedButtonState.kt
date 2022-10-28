package com.example.zeroapp.presentation.history

sealed class CheckedButtonState() {

    data class Filter(val pair: Pair<Long,Long>) : CheckedButtonState()

    object AllDays : CheckedButtonState()

    object LastWeek : CheckedButtonState()

    object CurrentMonth : CheckedButtonState()
}
