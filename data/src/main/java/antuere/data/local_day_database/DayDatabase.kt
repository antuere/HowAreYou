package antuere.data.local_day_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import antuere.data.local_day_database.converters.Converters
import antuere.data.local_day_database.entities.DayEntity

@Database(entities = [DayEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDatabaseDao: DayDatabaseDao

}