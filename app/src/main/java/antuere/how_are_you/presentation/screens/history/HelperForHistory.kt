package antuere.how_are_you.presentation.screens.history

import antuere.domain.dto.Day
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import java.time.Month

object HelperForHistory {

    private fun getMonthTitle(month: Month): Int {
        return when (month) {
            Month.JANUARY -> R.string.january_args
            Month.FEBRUARY -> R.string.february_args
            Month.MARCH -> R.string.march_args
            Month.APRIL -> R.string.april_args
            Month.MAY -> R.string.may_args
            Month.JUNE -> R.string.june_args
            Month.JULY -> R.string.july_args
            Month.AUGUST -> R.string.august_args
            Month.SEPTEMBER -> R.string.september_args
            Month.OCTOBER -> R.string.october_args
            Month.NOVEMBER -> R.string.november_args
            Month.DECEMBER -> R.string.december_args
        }
    }

    fun getHeaderForCalendar(month: Month): Int {
        return when (month) {
            Month.JANUARY -> R.string.january
            Month.FEBRUARY -> R.string.february
            Month.MARCH -> R.string.march
            Month.APRIL -> R.string.april
            Month.MAY -> R.string.may
            Month.JUNE -> R.string.june
            Month.JULY -> R.string.july
            Month.AUGUST -> R.string.august
            Month.SEPTEMBER -> R.string.september
            Month.OCTOBER -> R.string.october
            Month.NOVEMBER -> R.string.november
            Month.DECEMBER -> R.string.december
        }
    }

    fun getHeaderForHistory(dayList: List<Day>): UiText {
        val firstDay = TimeUtility.parseLongToLocalDate(dayList.first().dayId)
        val lastDay = TimeUtility.parseLongToLocalDate(dayList.last().dayId)

        return if (firstDay == lastDay) {
            val month = getMonthTitle(firstDay.month)
            UiText.StringResourceWithArg(resId = month, firstDay.dayOfMonth, firstDay.year)

        } else {
            val monthFirst = getMonthTitle(firstDay.month)
            val monthLast = getMonthTitle(lastDay.month)
            UiText.StringResourceConcat(
                resIdFirst = monthLast,
                firstArgs = arrayOf(lastDay.dayOfMonth, lastDay.year),
                resIdSecond = monthFirst,
                secondArgs = arrayOf(firstDay.dayOfMonth, firstDay.year),
                divider = "-"
            )
        }
    }
}