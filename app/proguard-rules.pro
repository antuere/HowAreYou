# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes Signature
-keep class antuere.data.network.remote_day_database.entities.DayEntityRemote.** { *; }
-keep class antuere.data.network.remote_day_database.entities.QuoteEntity.** { *; }
-keep class com.google.android.gms.internal.** { *; }
-keepclassmembers class antuere.data.network.remote_day_database.entities.QuoteEntity.** { *; }
-keepclassmembers class antuere.data.network.remote_day_database.entities.DayEntityRemote.** { *; }
-dontwarn antuere.data.local_day_database.mapping.DayEntityMapper
-dontwarn antuere.data.network.NetworkInfo
-dontwarn antuere.data.network.authentication_manager.AuthenticationManagerImpl
-dontwarn antuere.data.network.remote_day_database.FirebaseRealtimeDB
-dontwarn antuere.data.network.remote_day_database.mapping.DayEntityRemoteMapper
-dontwarn antuere.data.network.remote_day_database.mapping.QuoteEntityMapper
-dontwarn antuere.data.preferences_data_store.image_source_data_store.ImageSourceDataStore
-dontwarn antuere.data.preferences_data_store.quote_data_store.QuoteDataStore
-dontwarn antuere.data.preferences_data_store.settings_data_store.SettingsDataStore
-dontwarn antuere.data.preferences_data_store.settings_data_store.mapping.SettingsEntityMapper
-dontwarn antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
-dontwarn antuere.data.repository.DayRepositoryImpl
-dontwarn antuere.data.repository.HelplinesRepositoryImpl
-dontwarn antuere.data.repository.ImageSourceRepositoryImpl
-dontwarn antuere.data.repository.MentalTipsRepositoryImpl
-dontwarn antuere.data.repository.QuoteRepositoryImpl
-dontwarn antuere.data.repository.SettingsRepositoryImpl
-dontwarn antuere.data.repository.ToggleBtnRepositoryImpl
-dontwarn antuere.data.util.GalleryProvider
