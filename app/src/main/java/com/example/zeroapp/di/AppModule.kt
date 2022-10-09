package com.example.zeroapp.di

import android.app.Application
import android.content.Context
import com.example.zeroapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideTransitionName(@ApplicationContext context: Context): String {
        return context.getString(R.string.transition_name)
    }

}