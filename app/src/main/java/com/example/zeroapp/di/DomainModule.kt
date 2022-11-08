package com.example.zeroapp.di

import antuere.domain.repository.DayRepository
import antuere.domain.repository.QuoteRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.repository.ToggleBtnRepository
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
    fun provideRefreshRemoteDataUseCase(dayRepository: DayRepository): RefreshRemoteDataUseCase {
        return RefreshRemoteDataUseCase(dayRepository)
    }

    @Provides
    fun provideUpdDayQuoteRemoteUseCase(quoteRepository: QuoteRepository): UpdDayQuoteByRemoteUseCase {
        return UpdDayQuoteByRemoteUseCase(quoteRepository)
    }

    @Provides
    fun provideGetQuoteLocalUseCase(quoteRepository: QuoteRepository): GetDayQuoteLocalUseCase {
        return GetDayQuoteLocalUseCase(quoteRepository)
    }

    @Provides
    fun provideSaveQuoteLocalUseCase(quoteRepository: QuoteRepository): SaveDayQuoteLocalUseCase {
        return SaveDayQuoteLocalUseCase(quoteRepository)
    }

    @Provides
    fun provideGetSelectedDaysUseCase(dayRepository: DayRepository): GetSelectedDaysUseCase {
        return GetSelectedDaysUseCase(dayRepository)
    }

    @Provides
    fun provideGetCertainDaysUseCase(dayRepository: DayRepository): GetCertainDaysUseCase {
        return GetCertainDaysUseCase(dayRepository)
    }

    @Provides
    fun provideGetDaysByLimitUseCase(dayRepository: DayRepository): GetDaysByLimitUseCase {
        return GetDaysByLimitUseCase(dayRepository)
    }

    @Provides
    fun provideGetToggleBtnStateUseCase(toggleBtnRepository: ToggleBtnRepository): GetToggleBtnStateUseCase {
        return GetToggleBtnStateUseCase(toggleBtnRepository)
    }

    @Provides
    fun provideSaveToggleBtnStateUseCase(toggleBtnRepository: ToggleBtnRepository): SaveToggleBtnUseCase {
        return SaveToggleBtnUseCase(toggleBtnRepository)
    }

    @Provides
    fun provideGetSettingsUseCase(settingsRepository: SettingsRepository): GetSettingsUseCase {
        return GetSettingsUseCase(settingsRepository)
    }

    @Provides
    fun provideSaveSettingsUseCase(settingsRepository: SettingsRepository): SaveSettingsUseCase {
        return SaveSettingsUseCase(settingsRepository)
    }

    @Provides
    fun provideSavePinCodeUseCase(settingsRepository: SettingsRepository): SavePinCodeUseCase {
        return SavePinCodeUseCase(settingsRepository)
    }

    @Provides
    fun provideGetPinCodeUseCase(settingsRepository: SettingsRepository): GetSavedPinCodeUseCase {
        return GetSavedPinCodeUseCase(settingsRepository)
    }

    @Provides
    fun provideResetPinCodeUseCase(settingsRepository: SettingsRepository): ResetPinCodeUseCase {
        return ResetPinCodeUseCase(settingsRepository)
    }

    @Provides
    fun provideDeleteAllDaysLocalUseCase(dayRepository: DayRepository): DeleteAllDaysLocalUseCase {
        return DeleteAllDaysLocalUseCase(dayRepository)
    }

    @Provides
    fun provideDeleteAllDaysRemoteUseCase(dayRepository: DayRepository): DeleteAllDaysRemoteUseCase {
        return DeleteAllDaysRemoteUseCase(dayRepository)
    }

    @Provides
    fun provideDeleteAllSettingsUseCase(settingsRepository: SettingsRepository): DeleteAllSettingsUseCase {
        return DeleteAllSettingsUseCase(settingsRepository)
    }

}