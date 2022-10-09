package com.example.zeroapp.presentation.history.adapter

import antuere.domain.dto.Day
import java.util.Calendar

sealed class DataItem {

    data class DayItem(val day: Day) : DataItem() {
        override val id = day.dayId

    }

    data class Header(val calendar: Calendar) : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}