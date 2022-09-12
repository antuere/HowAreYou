package com.example.zeroapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.zeroapp.util.Converters
import timber.log.Timber

@Database(entities = [Day::class], version = 2)
@TypeConverters(Converters::class)
abstract class DayDatabase : RoomDatabase() {

    abstract val dayDatabaseDao: DayDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun getInstance(context: Context): DayDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    Timber.i("my log Create BD")
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DayDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                Timber.i("my log return BD")
                return instance
            }
        }
    }
}

val Migration1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            "ALTER TABLE day_table ADD COLUMN date_text TEXT DEFAULT 0 NOT NULL "
        )
        database.execSQL(
            "CREATE TABLE days_new (dayId INTEGER NOT NULL, date INTEGER NOT NULL, image_id INTEGER," +
                    "day_text TEXT, date_text TEXT NOT NULL, PRIMARY KEY (dayId))"
        )
        database.execSQL(
            "INSERT INTO days_new (dayId, date, image_id, day_text, date_text)" +
                    "SELECT dayId, date, image_id, day_text, date_text FROM day_table "
        )

        database.execSQL("DROP TABLE day_table")

        database.execSQL("ALTER TABLE days_new RENAME TO day_table")
    }

}