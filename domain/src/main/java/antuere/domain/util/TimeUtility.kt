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
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    fun formatLocalDate(localDate: LocalDate, format: String = DOT_FORMAT): String {
        return DateTimeFormatter.ofPattern(format).format(localDate)
    }

    fun getTimeInMilliseconds(localDate: LocalDate): Long {
//        val offset = TimeZone.getDefault().rawOffset

//        return if (offset > 0) {
//            localDate.toEpochDay() * 86400000 - offset
//        } else {
//            localDate.toEpochDay() * 86400000 + offset
//        }
        return localDate.toEpochDay() * 86400000

    }

    fun parseCurrentTime(format: String = DEFAULT_FORMAT): Date {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return sdf.parse(formatCurrentTime())
    }

    fun parse(date: Date, format: String = DEFAULT_FORMAT): Date {
        val currentFormat = formatDate(date)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return sdf.parse(currentFormat)
    }

    fun getDayOfMonth(): String {
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getCurrentMonthTime(): Long {
        val calendarTemp = Calendar.getInstance(TimeZone.getDefault())
        calendarTemp.set(Calendar.DAY_OF_MONTH, 1)

        val resultDate = parse(calendarTemp.time)
        return resultDate.time
    }

    fun getCurrentWeekTime(): Long {
        val calendarTemp = Calendar.getInstance(TimeZone.getDefault())
        calendarTemp.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val resultDate = parse(calendarTemp.time)
        return resultDate.time
    }

    fun parseLongToCalendar(timeInMillis: Long): Calendar {
        val calendarTemp = Calendar.getInstance(TimeZone.getDefault())
        calendarTemp.timeInMillis = timeInMillis
        return calendarTemp
    }

}