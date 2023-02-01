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
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
        return sdf.format(date)
    }

    fun formatLocalDate(localDate: LocalDate, format: TimeFormat = TimeFormat.Dot): String {
        return DateTimeFormatter.ofPattern(format.stringFormat).format(localDate)
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

    fun parseCurrentTime(format: TimeFormat = TimeFormat.Default): Date {
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
        return sdf.parse(formatCurrentTime())
    }

    fun parse(date: Date, format: TimeFormat = TimeFormat.Default): Date {
        val currentFormat = formatDate(date)
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
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
        val calendarTemp = Calendar.getInstance(TimeZone.getDefault()).apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }

        val resultDate = parse(calendarTemp.time)
        return resultDate.time
    }

    fun parseLongToCalendar(timeInMillis: Long): Calendar {
        return Calendar.getInstance(TimeZone.getDefault()).apply {
            this.timeInMillis = timeInMillis
        }
    }
}