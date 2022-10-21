package com.example.zeroapp.di

import antuere.domain.repository.DayRepository
import antuere.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {


    @Provides
    fun provideUpdateLastDayUseCase(dayRepository: DayRepository): UpdateLastDayUseCase {
        return UpdateLastDayUseCase(dayRepository)
    }

    @Provides
    fun provideGetAllDaysUseCase(dayRepository: DayRepository): GetAllDaysUseCase {
        return GetAllDaysUseCase(dayRepository)
    }

    @Provides
    fun provideGetFavoritesDaysUseCase(dayRepository: DayRepository): GetFavoritesDaysUseCase {
        return GetFavoritesDaysUseCase(dayRepository)
    }

    @Provides
    fun provideDeleteDayUseCase(dayRepository: DayRepository): DeleteDayUseCase {
        return DeleteDayUseCase(dayRepository)
    }

    @Provides
    fun provideGetDayByIdUseCase(dayRepository: DayRepository): GetDayByIdUseCase {
        return GetDayByIdUseCase(dayRepository)
    }

    @Provides
    fun provideAddDayUseCase(dayRepository: DayRepository): AddDayUseCase {
        return AddDayUseCase(dayRepository)
    }

    @Provides
    fun provideUpdateDayUseCase(dayRepository: DayRepository): UpdateDayUseCase {
        return UpdateDayUseCase(dayRepository)
    }

    @Provides
    fun provideUpdateFromFireBaseUseCase(dayRepository: DayRepository): UpdateFromFireBaseUseCase {
        return UpdateFromFireBaseUseCase(dayRepository)

    }

}