package antuere.how_are_you.presentation.screens.history

import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import java.util.Calendar

object HelperForHistory {

    private fun getMonthTitle(num: Int): Int {
        return when (num) {
            0 -> R.string.january_args
            1 -> R.string.february_args
            2 -> R.string.march_args
            3 -> R.string.april_args
            4 -> R.string.may_args
            5 -> R.string.june_args
            6 -> R.string.july_args
            7 -> R.string.august_args
            8 -> R.string.september_args
            9 -> R.string.october_args
            10 -> R.string.november_args
            11 -> R.string.december_args
            else -> throw IllegalArgumentException("Invalid number of month")
        }
    }

    fun getHeaderForCalendar(numOfMonth : Int) : Int {
        return when (numOfMonth) {
            0 -> R.string.january
            1 -> R.string.february
            2 -> R.string.march
            3 -> R.string.april
            4 -> R.string.may
            5 -> R.string.june
            6 -> R.string.july
            7 -> R.string.august
            8 -> R.string.september
            9 -> R.string.october
            10 -> R.string.november
            11 -> R.string.december
            else -> throw IllegalArgumentException("Invalid number of month")
        }
    }

    fun getHeaderForHistory(dayList: List<Day>): UiText {

        val firstDay = TimeUtility.parseLongToCalendar(dayList.first().dayId)
        val lastDay = TimeUtility.parseLongToCalendar(dayList.last().dayId)

        if (firstDay == lastDay) {
            val day = firstDay.get(Calendar.DAY_OF_MONTH)
            val month = getMonthTitle(firstDay.get(Calendar.MONTH))
            val year = firstDay.get(Calendar.YEAR)

            return UiText.StringResourceWithArg(resId = month, day, year)

        } else {

            val dayFirst = firstDay.get(Calendar.DAY_OF_MONTH)
            val monthFirst = getMonthTitle(firstDay.get(Calendar.MONTH))
            val yearFirst = firstDay.get(Calendar.YEAR)

            val dayLast = lastDay.get(Calendar.DAY_OF_MONTH)
            val monthLast = getMonthTitle(lastDay.get(Calendar.MONTH))
            val yearLast = lastDay.get(Calendar.YEAR)

            return UiText.StringResourceConcat(
                resIdFirst = monthLast,
                firstArgs = arrayOf(dayLast, yearLast),
                resIdSecond = monthFirst,
                secondArgs = arrayOf(dayFirst, yearFirst),
                divider = "-"
            )
        }
    }
}