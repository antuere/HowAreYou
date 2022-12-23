package com.example.zeroapp.di

import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.zeroapp.R
import antuere.data.preferences_data_store.quote_data_store.QuoteDataStore
import antuere.data.preferences_data_store.settings_data_store.SettingsDataStore
import com.example.zeroapp.util.ResourcesProvider
import com.example.zeroapp.presentation.history.MyAnalystForHistory
import antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
import antuere.data.remote.NetworkInfo
import com.example.zeroapp.presentation.home.MyAnalystForHome
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTransitionName(@ApplicationContext context: Context): String {
        return context.getString(R.string.transition_name)
    }

    @Provides
    fun provideMyAnalystForHistory(@ApplicationContext context: Context): MyAnalystForHistory {
        return MyAnalystForHistory(context)
    }

    @Provides
    fun provideMyAnalystForSummary(@ApplicationContext context: Context): MyAnalystForHome {
        return MyAnalystForHome(context)
    }

    @Provides
    fun provideResourcesProvider(@ApplicationContext context: Context): ResourcesProvider {
        return ResourcesProvider(context)
    }

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions()
                    .circleCrop()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.cat_placeholder)
                    .error(R.drawable.cat_black)
            )
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }


    @Provides
    @Singleton
    fun provideNetworkInfo(@ApplicationContext context: Context) : NetworkInfo {
        return NetworkInfo(context)
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideQuoteDataStore(@ApplicationContext context: Context): QuoteDataStore {
        return QuoteDataStore(context, "quote_data_store")
    }

    @Provides
    @Singleton
    fun provideToggleButtonDataStore(@ApplicationContext context: Context): ToggleBtnDataStore {
        return ToggleBtnDataStore(context, "toggle_button_data_store")
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context, "settings_data_store")
    }

    @Provides
    @Singleton
    fun provideMyBiometricManager(
        @ApplicationContext context: Context,
    ): UIBiometricDialog {
        return UIBiometricDialog(context)
    }
}