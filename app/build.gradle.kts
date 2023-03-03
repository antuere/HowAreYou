plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs")
    kotlin("android")
}

android {
    signingConfigs {
        create("release") {
//            storeFile file("C:\\Users\\AntuE\\AndroidStudioProjects\\HowAreYou\\keyStore\\keyStoreApp.jks")
            storeFile =
                file("C:\\Users\\user\\AndroidStudioProjects\\HowAreYou\\keyStoreAppModule.jks")
            storePassword = "anton1730"
            keyAlias = "key0"
            keyPassword = "anton1730"
        }
    }
    compileSdk = 33

    defaultConfig {
        applicationId = "antuere.how_are_you"
        minSdk = 24
        targetSdk = 33
        versionCode = 13 // versionName 13 - 0.5.4
        versionName = "0.5.4"

        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("benchmark") {
            initWith(getByName("release"))
            isDebuggable = false
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            proguardFiles("benchmark-rules.pro")
        }
    }

    android.applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName = "HowAreYou_v${defaultConfig.versionName}.apk"
            }
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xjvm-default=all-compatibility")
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    runtimeOnly("androidx.appcompat:appcompat:1.6.0")
    runtimeOnly("com.google.android.material:material:1.8.0")

// Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.0-alpha05")

// Compose
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui-util")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.activity:activity-compose:1.6.1")

// Accompanist
    implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

// Leak canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")

// Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.0")

// Biometric
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")

// Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.navigation:navigation-compose:2.5.3")

// Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.0-dev01")

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")

// FireBase
    implementation(platform("com.google.firebase:firebase-bom:31.2.2"))
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.android.gms:play-services-auth:20.4.1")

// Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

// Room
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

// Orbit MVI
    implementation("org.orbit-mvi:orbit-viewmodel:4.5.0")
    implementation("org.orbit-mvi:orbit-compose:4.5.0")

// Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:1.1.4")
    implementation("androidx.test:monitor:1.5.0")
    implementation("androidx.test.ext:junit-ktx:1.1.4")
    androidTestImplementation("junit:junit:4.13.2")

// ProfileInstaller
    implementation("androidx.profileinstaller:profileinstaller:1.3.0-beta01")

// Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

// Landscapist glide
    implementation("com.github.skydoves:landscapist-bom:2.1.2")
    implementation("com.github.skydoves:landscapist-glide")
    implementation("com.github.skydoves:landscapist-animation")

// Shimmer
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.3")

// DatePicker
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.9.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.2.2")

// Domain(module)
    implementation(project(":domain"))

// Data(module)
    implementation(project(":data"))

}

kapt {
    correctErrorTypes = true
}