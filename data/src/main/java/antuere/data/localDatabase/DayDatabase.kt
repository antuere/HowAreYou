package antuere.data.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import antuere.data.localDatabase.converters.Converters
import antuere.data.localDatabase.entities.DayEntity

@Database(entities = [DayEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDatabaseDao: DayDatabaseDao

}