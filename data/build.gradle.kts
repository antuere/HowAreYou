plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    id(libs.plugins.google.gms.googleServices.get().pluginId)
}

android {
    namespace = "antuere.data"
    compileSdk = 33

    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\AntuE\\AndroidStudioProjects\\HowAreYou\\keyStore\\keyStoreApp.jks")
            storePassword = "anton1730"
            keyAlias = "key0"
            keyPassword = "anton1730"
        }
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        signingConfig = signingConfigs.getByName("release")

        kapt {
            arguments {
                arg("room.schemaLocation", "${projectDir}/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    // Preferences DataStore
    implementation(libs.androidx.dataStore)

    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    // FireBase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.storageKtx)
    implementation(libs.google.firebase.databaseKtx)
    implementation(libs.google.firebase.authKtx)
    implementation(libs.play.services.auth)

    // Test
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.androidx.test.junit.ext)

    // Dagger - Hilt
    implementation(libs.google.hilt.android.core)
    kapt(libs.google.hilt.android.compiler)

    // Room
    implementation(libs.androidx.roomKtx)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    // Timber
    implementation(libs.timber)

    // Domain
    implementation(project(":domain"))
}