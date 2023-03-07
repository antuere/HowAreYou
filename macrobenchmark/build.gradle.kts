import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("com.android.test")
    kotlin("android")
}

android {
    namespace = "antuere.macrobenchmark"
    compileSdk = 33

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        managedDevices {
            devices {
                create("pixel2Api31", ManagedVirtualDevice::class) {
                    device = "Pixel 2"
                    apiLevel = 31
                    systemImageSource = "aosp"
                }
            }
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    defaultConfig {
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
        minSdk = 23
        targetSdk = 33
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // This benchmark buildType is used for benchmarking, and should function like your
        // release build (for example, with minification on). It"s signed with a debug key
        // for easy local/CI testing.
        create("benchmark") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            proguardFiles("benchmark-rules.pro")
        }
    }

    targetProjectPath = ":app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    // Test
    implementation(libs.androidx.test.junit.ext)
    implementation(libs.androidx.test.espresso)
    implementation(libs.androidx.test.uiautomator)

    // Macrobenchmark
    implementation(libs.androidx.benchmark.macro)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}