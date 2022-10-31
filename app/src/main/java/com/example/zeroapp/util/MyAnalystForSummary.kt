package com.example.zeroapp.util

import android.content.Context
import antuere.domain.dto.Day
import com.example.zeroapp.R

class MyAnalystForSummary(private val context: Context) {

    companion object {
        const val DEFAULT_WISH = -1
    }

    fun getWishStringForSummary(id: Int): String {
        return when (id) {
            R.drawable.smile_very_happy -> context.getString(R.string.wish_for_users_very_happy)
            R.drawable.smile_happy -> context.getString(R.string.wish_for_users_happy)
            R.drawable.smile_low -> context.getString(R.string.wish_for_users_low)
            R.drawable.smile_none -> context.getString(R.string.wish_for_users_none)
            R.drawable.smile_sad -> context.getString(R.string.wish_for_users_sad)
            else -> context.getString(R.string.wish_for_users_plug)
        }
    }


    fun isShowWarningForSummary(days: List<Day>): Boolean {
        var result = true

        days.forEach { day ->
            if (day.imageId != R.drawable.smile_none && day.imageId != R.drawable.smile_sad) {
                result = false
            }
        }

        return result
    }
}