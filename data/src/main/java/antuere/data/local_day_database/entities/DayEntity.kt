package antuere.data.local_day_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import antuere.domain.util.TimeUtility


@Entity(tableName = DayEntity.TABLE_NAME)
data class DayEntity(
    @PrimaryKey(autoGenerate = false)
    var dayId: Long = TimeUtility.parseCurrentTime().time,
    @ColumnInfo(name = "smile_image")
    val smileImage: SmileImage,
    @ColumnInfo(name = "day_text")
    var dayText: String,
    @ColumnInfo(name = "date_text")
    val dateString: String = TimeUtility.formatCurrentTime(),
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "day_table"
    }
}