package antuere.data.localDatabase.converters

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun calendarFromLong(value: Long?): Calendar? = value?.let { value_new ->
        GregorianCalendar().also { calendar ->
            calendar.timeInMillis = value_new
        }
    }

    @TypeConverter
    fun calendarToLong(timestamp: Calendar?): Long? = timestamp?.timeInMillis
}