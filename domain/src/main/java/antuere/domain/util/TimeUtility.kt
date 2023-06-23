package antuere.domain.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimeUtility {

    private val localDate: LocalDate
        get() = LocalDate.now()

    fun formatCurrentTime(format: TimeFormat = TimeFormat.Default): String {
        return formatLocalDate(localDate, format)
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

    private fun formatLocalDate(localDate: LocalDate, format: TimeFormat = TimeFormat.Dot): String {
        return DateTimeFormatter.ofPattern(format.stringFormat).format(localDate)
    }

    fun parseCurrentTime(format: TimeFormat = TimeFormat.Default): Date {
        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
        return sdf.parse(formatCurrentTime())
    }

    fun getDayOfMonth(): String {
        return localDate.dayOfMonth.toString()
    }

    fun getMonth(): Month {
        return localDate.month
    }

    fun getWeekNumber(): Int {
        return localDate[WeekFields.of(Locale.getDefault()).weekOfYear()]
    }

    fun getCurrentMonthTime(): Long {
        return localDate
            .withDayOfMonth(1)
            .atStartOfDay()
            .toInstant(OffsetDateTime.now().offset)
            .toEpochMilli()
    }

    fun getCurrentWeekTime(): Long {
        val startWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        return localDate
            .with(TemporalAdjusters.previousOrSame(startWeek))
            .atStartOfDay()
            .toInstant(OffsetDateTime.now().offset)
            .toEpochMilli()
    }

    fun parseLongToLocalDate(timeInMillis: Long): LocalDate {
        return Instant.ofEpochMilli(timeInMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
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

//    fun getTimeInMilliseconds(localDate: LocalDate): Long {
//        val utcTime = localDate.toEpochDay() * 86400000
//        return utcTime.convertFromUTC()
//    }

//    private fun parse(date: Date, format: TimeFormat = TimeFormat.Default): Date {
//        val currentFormat = formatDate(date)
//        val sdf = SimpleDateFormat(format.stringFormat, Locale.getDefault())
//        return sdf.parse(currentFormat)
//    }
}