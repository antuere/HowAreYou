package com.example.zeroapp.di

import android.content.Context
import antuere.data.remoteDataBase.FirebaseApi
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.zeroapp.R
import com.example.zeroapp.util.WishAnalyzer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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
    fun provideWishAnalyzer(@ApplicationContext context: Context): WishAnalyzer {
        return WishAnalyzer(context)
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
    fun provideFireBaseApi(auth: FirebaseAuth, realtimeDb: DatabaseReference): FirebaseApi {
        return FirebaseApi(auth, realtimeDb)
    }


}