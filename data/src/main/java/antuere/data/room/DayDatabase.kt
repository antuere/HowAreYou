package antuere.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import antuere.data.room.converters.Converters
import antuere.data.room.entities.DayEntity

@Database(entities = [DayEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDatabaseDao: DayDatabaseDao

}