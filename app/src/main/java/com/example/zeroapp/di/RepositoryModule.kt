package com.example.zeroapp.di

import antuere.data.repository.DayRepositoryImpl
import antuere.data.repository.QuoteRepositoryImpl
import antuere.data.repository.ToggleBtnRepositoryImpl
import antuere.domain.repository.DayRepository
import antuere.domain.repository.QuoteRepository
import antuere.domain.repository.ToggleBtnRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDayRepository(dayRepositoryImpl: DayRepositoryImpl): DayRepository

    @Binds
    @Singleton
    abstract fun bindQuoteRepository(quoteRepositoryImpl: QuoteRepositoryImpl): QuoteRepository

    @Binds
    @Singleton
    abstract fun bindToggleBtnStateRepository(toggleBtnRepositoryImpl: ToggleBtnRepositoryImpl): ToggleBtnRepository
}