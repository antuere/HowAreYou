package com.example.zeroapp.di

import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.privacy_manager.PrivacyManager
import antuere.domain.repository.DayRepository
import antuere.domain.repository.QuoteRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.usecases.authentication.*
import antuere.domain.usecases.day_quote.GetDayQuoteLocalUseCase
import antuere.domain.usecases.day_quote.SaveDayQuoteLocalUseCase
import antuere.domain.usecases.day_quote.UpdDayQuoteByRemoteUseCase
import antuere.domain.usecases.days_entities.*
import antuere.domain.usecases.privacy.*
import antuere.domain.usecases.user_settings.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {


    @Provides
    fun provideGetLastDayUseCase(dayRepository: DayRepository): GetLastDayUseCase {
        return GetLastDayUseCase(dayRepository)
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

    @Provides
    fun provideGetUserNicknameUseCase(settingsRepository: SettingsRepository): GetUserNicknameUseCase {
        return GetUserNicknameUseCase(settingsRepository)
    }

    @Provides
    fun provideSaveUserNicknameUseCase(settingsRepository: SettingsRepository): SaveUserNicknameUseCase {
        return SaveUserNicknameUseCase(settingsRepository)
    }

    @Provides
    fun provideResetUserNicknameUseCase(settingsRepository: SettingsRepository): ResetUserNicknameUseCase {
        return ResetUserNicknameUseCase(settingsRepository)
    }

    @Provides
    fun provideCheckCurrentAuthUseCase(authenticationManager: AuthenticationManager): CheckCurrentAuthUseCase {
        return CheckCurrentAuthUseCase(authenticationManager)
    }

    @Provides
    fun provideGetUserNameFromServerUseCase(authenticationManager: AuthenticationManager): GetUserNameFromServerUseCase {
        return GetUserNameFromServerUseCase(authenticationManager)
    }

    @Provides
    fun provideSetUserNicknameOnServerUseCase(authenticationManager: AuthenticationManager): SetUserNicknameOnServerUseCase {
        return SetUserNicknameOnServerUseCase(authenticationManager)
    }

    @Provides
    fun provideSignInUseCase(authenticationManager: AuthenticationManager): SignInUseCase {
        return SignInUseCase(authenticationManager)
    }

    @Provides
    fun provideSignUpUseCase(authenticationManager: AuthenticationManager): SignUpUseCase {
        return SignUpUseCase(authenticationManager)
    }

    @Provides
    fun provideSignOutUseCase(authenticationManager: AuthenticationManager): SignOutUseCase {
        return SignOutUseCase(authenticationManager)
    }

    @Provides
    fun provideResetPasswordUseCase(authenticationManager: AuthenticationManager): ResetPasswordUseCase {
        return ResetPasswordUseCase(authenticationManager)
    }

    @Provides
    fun provideSignInByGoogleUseCase(authenticationManager: AuthenticationManager): SignInByGoogleUseCase {
        return SignInByGoogleUseCase(authenticationManager)
    }

    @Provides
    fun provideDoneAuthByPinUseCase(privacyManager: PrivacyManager): DoneAuthByPinUseCase {
        return DoneAuthByPinUseCase(privacyManager)
    }

    @Provides
    fun provideDoneAuthByBiometricUseCase(privacyManager: PrivacyManager): DoneAuthByBiometricUseCase {
        return DoneAuthByBiometricUseCase(privacyManager)
    }

    @Provides
    fun provideResetAuthByPinUseCase(privacyManager: PrivacyManager): ResetAuthByPinUseCase {
        return ResetAuthByPinUseCase(privacyManager)
    }

    @Provides
    fun provideResetAuthByBiometricUseCase(privacyManager: PrivacyManager): ResetAuthByBiometricUseCase {
        return ResetAuthByBiometricUseCase(privacyManager)
    }

    @Provides
    fun provideCheckAuthByPinUseCase(privacyManager: PrivacyManager): CheckAuthByPinUseCase {
        return CheckAuthByPinUseCase(privacyManager)
    }

    @Provides
    fun provideCheckAuthByBiometricUseCase(privacyManager: PrivacyManager): CheckAuthByBiometricUseCase {
        return CheckAuthByBiometricUseCase(privacyManager)
    }


}