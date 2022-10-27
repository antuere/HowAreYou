package antuere.domain.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtility {
    private const val DEFAULT_FORMAT = "dd/MM/yy"
    val calendar: Calendar
        get() = Calendar.getInstance()
    private val currentDate: Date
        get() = calendar.time

    fun formatCurrentTime(format: String = DEFAULT_FORMAT): String {
        return format(currentDate, format)
    }

    fun format(date: Date, format: String = DEFAULT_FORMAT): String {
        return SimpleDateFormat(format, Locale.US).format(date)
    }

    fun parseFormat(format: String = DEFAULT_FORMAT): Date {
        val currentDateFormat = formatCurrentTime()
        return SimpleDateFormat(format, Locale.US).parse(currentDateFormat)
    }


    fun getDayOfMonth(): String {
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getCurrentMonthTime() : Long {
        val calendarTemp =  Calendar.getInstance()
        calendarTemp.set(Calendar.DAY_OF_MONTH, 1)
        return calendarTemp.timeInMillis
    }

}