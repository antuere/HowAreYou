package com.example.zeroapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.zeroapp.util.MyBiometricManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
class MyBiometricManagerModule {

    @Provides
    fun provideMyBiometricManager(
        @ApplicationContext context: Context,
        fragment: Fragment
    ): MyBiometricManager {
        return MyBiometricManager(context, fragment)
    }

}