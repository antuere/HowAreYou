package com.example.zeroapp.di

import android.app.Application
import androidx.room.Room
import antuere.data.localDatabase.DayDatabase
import antuere.data.localDatabase.mappers.DayEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDayDatabase(application: Application): DayDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            DayDatabase::class.java,
            "day_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDayEntityMapper(): DayEntityMapper {
        return DayEntityMapper()
    }
}