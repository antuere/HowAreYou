package com.example.zeroapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import antuere.data.room.DayDatabase
import antuere.data.room.mapping.DayEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDayDatabase(@ApplicationContext context:Context): DayDatabase {
        return Room.databaseBuilder(
            context,
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