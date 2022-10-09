package com.example.zeroapp.presentation.history.adapter

import android.view.View
import antuere.domain.dto.Day

interface DayClickListener {

    fun onClick(day: Day, view: View)

    fun onClickLong(day: Day)
}