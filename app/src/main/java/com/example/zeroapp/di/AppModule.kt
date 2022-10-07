package com.example.zeroapp.di

import android.app.Application
import com.example.zeroapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideTransitionName(application: Application): String {
        return application.getString(R.string.transition_name)
    }

}