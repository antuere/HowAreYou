package com.example.zeroapp.util

import android.content.Context
import com.example.zeroapp.R
import java.util.Calendar

class MyAnalystForHistory(private val context: Context) {


    private fun getMonthTitle(num: Int): String {
        return when (num) {
            0 -> context.resources.getString(R.string.january)
            1 -> context.resources.getString(R.string.february)
            2 -> context.resources.getString(R.string.march)
            3 -> context.resources.getString(R.string.april)
            4 -> context.resources.getString(R.string.may)
            5 -> context.resources.getString(R.string.june)
            6 -> context.resources.getString(R.string.july)
            7 -> context.resources.getString(R.string.august)
            8 -> context.resources.getString(R.string.september)
            9 -> context.resources.getString(R.string.october)
            10 -> context.resources.getString(R.string.november)
            11 -> context.resources.getString(R.string.december)
            else -> throw IllegalArgumentException("Invalid number of month")
        }
    }

    fun getHeaderForHistory(firstDay: Calendar, lastDay: Calendar): String {

        if (firstDay == lastDay) {
            val day = firstDay.get(Calendar.DAY_OF_MONTH)
            val month = getMonthTitle(firstDay.get(Calendar.MONTH))
            val year = firstDay.get(Calendar.YEAR)

            return "$day $month $year"
        } else {

            val dayFirst = firstDay.get(Calendar.DAY_OF_MONTH)
            val monthFirst = getMonthTitle(firstDay.get(Calendar.MONTH))
            val yearFirst = firstDay.get(Calendar.YEAR)

            val dayLast = lastDay.get(Calendar.DAY_OF_MONTH)
            val monthLast = getMonthTitle(lastDay.get(Calendar.MONTH))
            val yearLast = lastDay.get(Calendar.YEAR)

            return "$dayLast $monthLast $yearLast - $dayFirst $monthFirst $yearFirst"
        }

    }
}