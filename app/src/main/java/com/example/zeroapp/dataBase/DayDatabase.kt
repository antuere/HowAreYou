package com.example.zeroapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import timber.log.Timber

@Database(entities = [Day::class], version = 1)
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
                    ).build()
                    INSTANCE = instance
                }
                Timber.i("my log return BD")
                return instance
            }
        }
    }
}