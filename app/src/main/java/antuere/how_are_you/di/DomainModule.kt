package antuere.how_are_you.di

import antuere.domain.repository.*
import antuere.domain.usecases.AddDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideAddDayUseCase(dayRepository: DayRepository): AddDayUseCase {
        return AddDayUseCase(dayRepository)
    }
}