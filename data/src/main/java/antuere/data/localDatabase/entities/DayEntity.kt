package antuere.data.localDatabase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import antuere.domain.util.TimeUtility
import java.util.*


@Entity(tableName = DayEntity.TABLE_NAME)
data class DayEntity(

    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0L,

    @ColumnInfo(name = "date")
    val currentDate: Calendar = TimeUtility.calendar,

    @ColumnInfo(name = "image_id")
    val imageId: Int,

    @ColumnInfo(name = "day_text")
    var dayText: String,

    @ColumnInfo(name = "date_text")
    val currentDateString: String = TimeUtility.formatCurrentTime()

) {
    companion object {
        const val TABLE_NAME = "day_table"
    }
}