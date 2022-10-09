package antuere.domain.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtility {
    const val DEFAULT_FORMAT = "dd/MM/yy"
    val calendar: Calendar
        get() = Calendar.getInstance()
    val currentDate: Date
        get() = calendar.time

    fun formatCurrentTime(format: String = DEFAULT_FORMAT) = format(currentDate, format)
    fun format(date: Date, format: String = DEFAULT_FORMAT): String {
        return SimpleDateFormat(format, Locale.US).format(date)
    }

}