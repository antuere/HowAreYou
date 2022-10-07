package com.example.zeroapp.di

import antuere.domain.repository.DayRepository
import antuere.domain.usecases.DeleteDayUseCase
import antuere.domain.usecases.GetAllDaysUseCase
import antuere.domain.usecases.GetCurrentDateUseCase
import antuere.domain.usecases.UpdateLastDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideGetCurrentDayUseCase(): GetCurrentDateUseCase {
        return GetCurrentDateUseCase()
    }

    @Provides
    fun provideUpdateLastDayUseCase(dayRepository: DayRepository): UpdateLastDayUseCase {
        return UpdateLastDayUseCase(dayRepository)
    }

    @Provides
    fun provideGetAllDaysUseCase(dayRepository: DayRepository): GetAllDaysUseCase {
        return GetAllDaysUseCase(dayRepository)
    }

    @Provides
    fun provideDeleteDayUseCase(dayRepository: DayRepository): DeleteDayUseCase {
        return DeleteDayUseCase(dayRepository)
    }

}