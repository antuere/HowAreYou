package antuere.how_are_you.di

import android.content.Context
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
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

//    @Provides
//    @Singleton
//    fun provideGlide(@ApplicationContext context: Context): RequestManager {
//        return Glide.with(context)
//            .setDefaultRequestOptions(
//                RequestOptions()
//                    .circleCrop()
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .placeholder(R.drawable.cat_placeholder)
//                    .error(R.drawable.cat_black)
//            )
//    }

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
    fun provideMyBiometricManager(
        @ApplicationContext context: Context,
    ): UIBiometricDialog {
        return UIBiometricDialog(context)
    }
}