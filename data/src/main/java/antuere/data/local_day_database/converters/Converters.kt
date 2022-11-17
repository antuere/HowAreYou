package antuere.data.local_day_database.converters

import androidx.room.TypeConverter
import antuere.data.local_day_database.entities.SmileImage
import java.util.*

class Converters {
    @TypeConverter
    fun toSmileImage(value: String) = enumValueOf<SmileImage>(value)

    @TypeConverter
    fun fromSmileImage(value: SmileImage) = value.name
}