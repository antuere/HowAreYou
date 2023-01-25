package antuere.domain.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtility {

    private val calendar: Calendar
        get() = Calendar.getInstance()

    private val currentDate: Date
        get() = calendar.time

    fun formatCurrentTime(format: TimeFormat = TimeFormat.Default): String {
        return formatDate(currentDate, format)
    }

    fun formatDate(date: Date, format: TimeFormat = TimeFormat.Default): String {
        return SimpleDateFormat(format.stringFormat, Locale.getDefault()).format(date)
    }

    fun formatLocalDate(localDate: LocalDate, format: TimeFormat = TimeFormat.Dot): String {
        return DateTimeFormatter.ofPattern(format.stringFormat).format(localDate)
    }

    fun getTimeInMilliseconds(localDate: LocalDate): Long {
        return localDate.toEpochDay() * 86400000
    }

    fun parseCurrentTime(format: TimeFormat = TimeFormat.Default): Date {
        val currentTimeFormatted = formatCurrentTime()
        return SimpleDateFormat(format.stringFormat, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(currentTimeFormatted)
    }

    fun parse(date: Date, format: TimeFormat = TimeFormat.Default): Date {
        val currentDateFormatted = formatDate(date)
        return SimpleDateFormat(format.stringFormat, Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.parse(currentDateFormatted)
    }

    fun getDayOfMonth(): String {
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getCurrentMonthTime(): Long {
        val timezone = TimeZone.getDefault()
        val calendar = Calendar.getInstance(timezone).apply {
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val resultDate = parse(calendar.time)
        return resultDate.time
    }

    fun getCurrentWeekTime(): Long {
        val timezone = TimeZone.getDefault()
        val calendar = Calendar.getInstance(timezone).apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        val resultDate = parse(calendar.time)
        return resultDate.time
    }

    fun parseLongToCalendar(timeInMillis: Long): Calendar {
        val timezone = TimeZone.getDefault()
        return Calendar.getInstance(timezone).apply {
            this.timeInMillis = timeInMillis
        }
    }

}