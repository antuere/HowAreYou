package com.example.zeroapp.di

import antuere.data.repository.DayRepositoryImpl
import antuere.data.repository.DayRepositoryImplRemote
import antuere.domain.repository.DayRepository
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
    abstract fun bindRepository(dayRepositoryImpl: DayRepositoryImpl): DayRepository
}