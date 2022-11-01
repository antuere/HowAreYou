package com.example.zeroapp.presentation.summary

import android.content.Context
import antuere.domain.dto.Day
import com.example.zeroapp.R
import com.example.zeroapp.util.SmileProvider

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

        if (days.isEmpty()) return false

        var result = true

        days.forEach { day ->
            val resId = SmileProvider.getSmileImageByName(day.imageName)
            if (resId != R.drawable.smile_none && resId != R.drawable.smile_sad) {
                result = false
            }
        }

        return result
    }
}