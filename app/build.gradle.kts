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
        versionCode = 14 // versionName 14 - 0.5.5
        versionName = "0.5.5"

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

    implementation(libs.kotlin.immutable.collections)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.legacy)
    runtimeOnly(libs.androidx.appcompat)
    runtimeOnly(libs.google.android.material)


    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
    androidTestImplementation(composeBom)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.test.junut4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Accompanist
    implementation(libs.bundles.accompanist)

    // Leak canary
    debugImplementation(libs.leakCanary)

    // Splash Screen API
    implementation(libs.androidx.splashScreen)

    // Biometric
    implementation(libs.androidx.biometricKtx)

    // Navigation
    implementation(libs.bundles.navigation)

    // Preferences DataStore
    implementation(libs.androidx.dataStore)

    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    // FireBase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.play.services.auth)

    // Dagger - Hilt
    implementation(libs.google.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.google.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)

    // Room
    implementation(libs.androidx.roomKtx)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    // Orbit MVI
    implementation(libs.orbit.viewmodel)
    implementation(libs.orbit.compose)

    // Test
    testImplementation(libs.test.junit)
    implementation(libs.androidx.test.junit.ext)
    implementation(libs.androidx.test.monitor)
//    implementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation(libs.test.junit)

    // ProfileInstaller
    implementation(libs.androidx.profileInstaller)

    // Timber
    implementation(libs.timber)

    // Landscapist glide
    implementation(libs.bundles.landscapist)

    // Shimmer
    implementation(libs.shimmer)

    // DatePicker
    implementation(libs.datepicker)
    coreLibraryDesugaring(libs.android.tools.desugar.jdk.libs)

    // Domain(module)
    implementation(project(":domain"))

    // Data(module)
    implementation(project(":data"))

}

kapt {
    correctErrorTypes = true
}