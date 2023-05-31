package antuere.how_are_you.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import antuere.data.local_day_database.DayDatabase
import antuere.data.local_day_database.mapping.DayEntityMapper
import antuere.data.network.NetworkInfo
import antuere.data.network.remote_day_database.mapping.DayEntityRemoteMapper
import antuere.data.network.remote_day_database.mapping.QuoteEntityMapper
import antuere.data.preferences_data_store.image_source_data_store.ImageSourceDataStore
import antuere.data.preferences_data_store.quote_data_store.QuoteDataStore
import antuere.data.preferences_data_store.settings_data_store.SettingsDataStore
import antuere.data.preferences_data_store.settings_data_store.mapping.SettingsEntityMapper
import antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
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

    @Provides
    @Singleton
    fun provideNetworkInfo(@ApplicationContext context: Context): NetworkInfo {
        return NetworkInfo(context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideQuoteDataStore(@ApplicationContext context: Context): QuoteDataStore {
        return QuoteDataStore(context, "quote_data_store")
    }

    @Provides
    @Singleton
    fun provideToggleButtonDataStore(@ApplicationContext context: Context): ToggleBtnDataStore {
        return ToggleBtnDataStore(context, "toggle_button_data_store")
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context, "settings_data_store")
    }

    @Provides
    @Singleton
    fun provideImageSourceDataStore(@ApplicationContext context: Context): ImageSourceDataStore {
        return ImageSourceDataStore(context, "image_source_data_store")
    }

}