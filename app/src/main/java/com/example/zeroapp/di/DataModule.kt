package com.example.zeroapp.di

import android.content.Context
import androidx.room.Room
import antuere.data.local_day_database.DayDatabase
import antuere.data.local_day_database.mapping.DayEntityMapper
import antuere.data.remote.remote_day_database.mapping.QuoteEntityMapper
import antuere.data.preferences_data_store.settings_data_store.mapping.SettingsEntityMapper
import antuere.data.preferences_data_store.toggle_btn_data_store.mapping.ToggleBtnStateEntityMapper
import antuere.data.remote.remote_day_database.mapping.DayEntityRemoteMapper
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
    fun provideDayEntityMapperRemote(): DayEntityRemoteMapper {
        return DayEntityRemoteMapper()
    }

    @Provides
    @Singleton
    fun provideToggleBtnStateEntityMapper(): ToggleBtnStateEntityMapper {
        return ToggleBtnStateEntityMapper()
    }

    @Provides
    @Singleton
    fun provideQuotEntityMapper(): QuoteEntityMapper {
        return QuoteEntityMapper()
    }

    @Provides
    @Singleton
    fun provideSettingsEntityMapper(): SettingsEntityMapper {
        return SettingsEntityMapper()
    }

    @Provides
    @Singleton
    fun provideDayDatabaseRemote(): DatabaseReference {
        return Firebase.database.reference
    }


}