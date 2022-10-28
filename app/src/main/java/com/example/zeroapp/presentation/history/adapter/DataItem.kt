package com.example.zeroapp.presentation.history.adapter

import antuere.domain.dto.Day

sealed class DataItem {

    data class DayItem(val day: Day) : DataItem() {
        override val id = day.dayId

    }

    data class Header(val dateString: String) : DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}