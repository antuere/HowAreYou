package com.example.zeroapp.di

import android.content.Context
import androidx.room.Room
import antuere.data.localDatabase.DayDatabase
import antuere.data.localDatabase.mapping.DayEntityMapper
import antuere.data.remoteDataBase.mapping.DayEntityMapperRemote
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
    fun provideDayDatabase(@ApplicationContext context: Context): DayDatabase {
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

    @Provides
    @Singleton
    fun provideDayEntityMapperRemote(): DayEntityMapperRemote {
        return DayEntityMapperRemote()
    }

    @Provides
    @Singleton
    fun provideDayDatabaseRemote(): DatabaseReference {
        return Firebase.database.reference
    }
}