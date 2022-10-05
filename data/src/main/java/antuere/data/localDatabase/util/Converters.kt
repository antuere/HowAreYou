package antuere.data.localDatabase.util

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? = value?.let { value_new ->
        GregorianCalendar().also { calendar ->
            calendar.timeInMillis = value_new
        }
    }

    @TypeConverter
    fun toTimestamp(timestamp: Calendar?): Long? = timestamp?.timeInMillis
}