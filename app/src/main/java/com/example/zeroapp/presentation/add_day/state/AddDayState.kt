package com.example.zeroapp.presentation.add_day.state

data class AddDayState(
    val smileImages: List<Int> = listOf(
        antuere.data.R.drawable.smile_sad,
        antuere.data.R.drawable.smile_none,
        antuere.data.R.drawable.smile_low,
        antuere.data.R.drawable.smile_happy,
        antuere.data.R.drawable.smile_very_happy
    )
)
