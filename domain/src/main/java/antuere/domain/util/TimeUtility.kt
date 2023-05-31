package antuere.domain.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimeUtility {

    private val calendar: Calendar
        get() = Calendar.getInstance()

    private val currentDate: Date
        get() = calendar.time

    fun formatCurrentTime(format: TimeFormat = TimeFormat.Default): String {
        return formatDate(currentDate, format)
    }

    fun formatDate(
        date: Date,
        format: TimeFormat = TimeFormat.Default,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): String {
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
        sdf.timeZone = timeZone
        return sdf.format(date)
    }

    fun formatLocalDate(localDate: LocalDate, format: TimeFormat = TimeFormat.Dot): String {
        return DateTimeFormatter.ofPattern(format.stringFormat).format(localDate)
    }

    fun getTimeInMilliseconds(localDate: LocalDate): Long {
        val utcTime = localDate.toEpochDay() * 86400000
        return utcTime.convertFromUTC()
    }

    fun parseCurrentTime(format: TimeFormat = TimeFormat.Default): Date {
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
        return sdf.parse(formatCurrentTime())
    }

    private fun parse(date: Date, format: TimeFormat = TimeFormat.Default): Date {
        val currentFormat = formatDate(date)
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
        return sdf.parse(currentFormat)
    }

    fun getDayOfMonth(): String {
        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }

    fun getMonthNumber(): Int {
        return calendar.get(Calendar.MONTH)
    }

    fun getWeekNumber(): Int {
        return calendar.get(Calendar.WEEK_OF_YEAR)
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

    fun isNeedLockApp(appClosingTime: Long): Boolean {
        if (appClosingTime == 0L) return false
        val tenMinutesInMillis = 600000L
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - appClosingTime
        return timeDifference >= tenMinutesInMillis
    }

    fun Long.convertToUTC(timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Long {
        val sdf = SimpleDateFormat(TimeFormat.Default.stringFormat, Locale.getDefault())
        val dateStringCurrentZone = formatDate(Date(this))
        sdf.timeZone = timeZone
        return sdf.parse(dateStringCurrentZone).time
    }

    fun Long.convertFromUTC(): Long {
        val calendarTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            this.timeInMillis = this@convertFromUTC
        }
        return Calendar.getInstance().apply {
            this.clear()
            set(
                calendarTemp.get(Calendar.YEAR),
                calendarTemp.get(Calendar.MONTH),
                calendarTemp.get(Calendar.DAY_OF_MONTH),
            )
        }.timeInMillis
    }
}