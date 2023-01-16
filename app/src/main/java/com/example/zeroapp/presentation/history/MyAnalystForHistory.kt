package com.example.zeroapp.presentation.history

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText
import timber.log.Timber
import java.util.*

object MyAnalystForHistory {

    private fun getMonthTitle(num: Int): UiText {
        return when (num) {
            0 -> UiText.StringResource(R.string.january)
            1 -> UiText.StringResource(R.string.february)
            2 -> UiText.StringResource(R.string.march)
            3 -> UiText.StringResource(R.string.april)
            4 -> UiText.StringResource(R.string.may)
            5 -> UiText.StringResource(R.string.june)
            6 -> UiText.StringResource(R.string.july)
            7 -> UiText.StringResource(R.string.august)
            8 -> UiText.StringResource(R.string.september)
            9 -> UiText.StringResource(R.string.october)
            10 -> UiText.StringResource(R.string.november)
            11 -> UiText.StringResource(R.string.december)
            else -> throw IllegalArgumentException("Invalid number of month")
        }
    }

    fun getHeaderForHistory(firstDay: Calendar, lastDay: Calendar, context: Context): String {

        firstDay.timeZone = TimeZone.getTimeZone("UTC")
        lastDay.timeZone = TimeZone.getTimeZone("UTC")

        Timber.i("date errors : recompose get Header}")

        if (firstDay == lastDay) {
            val day = firstDay.get(Calendar.DAY_OF_MONTH)
            val month = getMonthTitle(firstDay.get(Calendar.MONTH)).asString(context)
            val year = firstDay.get(Calendar.YEAR)

            return "$day $month $year"
        } else {

            val dayFirst = firstDay.get(Calendar.DAY_OF_MONTH)
            val monthFirst = getMonthTitle(firstDay.get(Calendar.MONTH)).asString(context)
            val yearFirst = firstDay.get(Calendar.YEAR)

            val dayLast = lastDay.get(Calendar.DAY_OF_MONTH)
            val monthLast = getMonthTitle(lastDay.get(Calendar.MONTH)).asString(context)
            val yearLast = lastDay.get(Calendar.YEAR)

            return "$dayLast $monthLast $yearLast - $dayFirst $monthFirst $yearFirst"
        }
    }
}