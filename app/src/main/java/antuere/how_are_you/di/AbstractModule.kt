package antuere.how_are_you.di

import antuere.data.network.authentication_manager.AuthenticationManagerImpl
import antuere.data.network.remote_day_database.FirebaseRealtimeDB
import antuere.data.repository.*
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.remote_db.RemoteDbApi
import antuere.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    @Singleton
    abstract fun bindDayRepository(dayRepositoryImpl: DayRepositoryImpl): DayRepository

    @Binds
    @Singleton
    abstract fun bindQuoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository

    @Binds
    @Singleton
    abstract fun bindToggleBtnStateRepository(toggleBtnRepositoryImpl: ToggleBtnRepositoryImpl): ToggleBtnRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository


    @Binds
    @Singleton
    abstract fun bindMentalTipsRepository(mentalTipsRepositoryImpl: MentalTipsRepositoryImpl): MentalTipsRepository

    @Binds
    @Singleton
    abstract fun bindHelplinesRepository(helplinesRepositoryImpl: HelplinesRepositoryImpl): HelplinesRepository

    @Binds
    @Singleton
    abstract fun bindAuthManager(authenticationManagerImpl: AuthenticationManagerImpl): AuthenticationManager

    @Binds
    @Singleton
    abstract fun bindRemoteDbApi(firebaseRealtimeDB: FirebaseRealtimeDB): RemoteDbApi

    @Binds
    @Singleton
    abstract fun bindImageSourceRepository(imageSourceRepositoryImpl: ImageSourceRepositoryImpl): ImageSourceRepository

}