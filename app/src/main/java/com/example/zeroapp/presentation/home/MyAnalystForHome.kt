package com.example.zeroapp.presentation.home

import android.content.Context
import antuere.domain.dto.Day
import com.example.zeroapp.R

class MyAnalystForHome(private val context: Context) {

    companion object {
        const val DEFAULT_WISH = -1
    }

    fun getWishStringForSummary(id: Int): String {
        return when (id) {
            antuere.data.R.drawable.smile_very_happy -> context.getString(R.string.wish_for_users_very_happy)
            antuere.data.R.drawable.smile_happy -> context.getString(R.string.wish_for_users_happy)
            antuere.data.R.drawable.smile_low -> context.getString(R.string.wish_for_users_low)
            antuere.data.R.drawable.smile_none -> context.getString(R.string.wish_for_users_none)
            antuere.data.R.drawable.smile_sad -> context.getString(R.string.wish_for_users_sad)
            else -> context.getString(R.string.wish_for_users_plug)
        }
    }


    fun isShowWarningForSummary(days: List<Day>): Boolean {
        if (days.isEmpty()) return false

        var result = true

        days.forEach { day ->
            if (day.imageResId != antuere.data.R.drawable.smile_none && day.imageResId != antuere.data.R.drawable.smile_sad) {
                result = false
            }
        }

        return result
    }
}