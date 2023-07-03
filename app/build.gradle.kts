import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly
import com.android.build.gradle.internal.tasks.FinalizeBundleTask
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    id(libs.plugins.google.gms.googleServices.get().pluginId)
    id(libs.plugins.google.firebase.crashlytics.get().pluginId)
    id(libs.plugins.google.firebase.perf.get().pluginId)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()

if(keystorePropertiesFile.exists()){
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "antuere.how_are_you"
    compileSdk = 33

    signingConfigs {
        create("release_apk") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    defaultConfig {
        applicationId = "antuere.how_are_you"
        minSdk = 24
        targetSdk = 33
        versionCode = 29 // versionName 29 - 1.0.0
        versionName = "1.0.0"

        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release_apk")

        vectorDrawables {
            useSupportLibrary = true
        }

        resourceConfigurations += listOf("en", "ru")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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
        val buildTypeName = this.buildType.name
        outputs.all {
            val aabPackageName = "HowAreYou_v${defaultConfig.versionName}_$buildTypeName.aab"
            val bundleFinalizeTaskName = StringBuilder("sign").run {
                productFlavors.forEach {
                    append(it.name.capitalizeAsciiOnly())
                }
                append(buildType.name.capitalizeAsciiOnly())
                append("Bundle")
                toString()
            }
            tasks.named(bundleFinalizeTaskName, FinalizeBundleTask::class.java) {
                val file = finalBundleFile.asFile.get()
                val finalFile = File(file.parentFile, aabPackageName)
                finalBundleFile.set(finalFile)
            }

            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName = "HowAreYou_v${defaultConfig.versionName}_$buildTypeName.apk"
            }
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs += listOf("-Xjvm-default=all-compatibility")
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {

    // Immutable collections
    implementation(libs.kotlin.immutable.collections)

    // Core-ktx
    implementation(libs.androidx.coreKtx)

    // Legacy
    implementation(libs.androidx.legacy)

    // Appcompat
    runtimeOnly(libs.androidx.appcompat)

    // Material
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
    androidTestImplementation(libs.test.junit)

    // ProfileInstaller
    implementation(libs.androidx.profileInstaller)

    // TextFlow
    implementation(libs.textFlow)

    // Timber
    implementation(libs.timber)

    // Landscapist glide
    implementation(libs.bundles.landscapist)

    // Shimmer
    implementation(libs.shimmer)

    // LibraryDesugaring
    coreLibraryDesugaring(libs.android.tools.desugar.jdk.libs)

    // Domain(module)
    implementation(project(":domain"))

    // Data(module)
    implementation(project(":data"))
}

kapt {
    correctErrorTypes = true
}