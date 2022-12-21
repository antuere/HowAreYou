package antuere.domain.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtility {
    private const val DEFAULT_FORMAT = "dd/MM/yy"
    private const val DOT_FORMAT = "dd.MM.yy"

    private val calendar: Calendar
        get() = Calendar.getInstance()

    private val currentDate: Date
        get() = calendar.time

    fun formatCurrentTime(format: String = DEFAULT_FORMAT): String {
        return formatDate(currentDate, format)
    }

    fun formatDate(date: Date, format: String = DEFAULT_FORMAT): String {
        return SimpleDateFormat(format, Locale.US).format(date)
    }

    fun formatLocalDate(localDate: LocalDate, format: String = DOT_FORMAT): String {
        return DateTimeFormatter.ofPattern(format).format(localDate)
    }

    fun getTimeInMilliseconds(localDate: LocalDate) : Long {
        return localDate.toEpochDay() * 86400000
    }

    fun parseCurrentTime(format: String = DEFAULT_FORMAT): Date {
        return SimpleDateFormat(format, Locale.US).parse(formatCurrentTime())
    }

    fun parse(date: Date, format: String = DEFAULT_FORMAT): Date {
        val currentFormat = formatDate(date)
        return SimpleDateFormat(format, Locale.US).parse(currentFormat)
    }

    fun getDayOfMonth(): String {
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getCurrentMonthTime(): Long {
        val calendarTemp = Calendar.getInstance(Locale.US)
        calendarTemp.set(Calendar.DAY_OF_MONTH, 1)

        val resultDate = parse(calendarTemp.time)
        return resultDate.time
    }

    fun getCurrentWeekTime(): Long {
        val calendarTemp = Calendar.getInstance(Locale.US)
        calendarTemp.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val resultDate = parse(calendarTemp.time)
        return resultDate.time
    }

    fun parseLongToCalendar(timeInMillis: Long): Calendar {
        val calendar = Calendar.getInstance(Locale.US)
        calendar.timeInMillis = timeInMillis
        return calendar
    }

}