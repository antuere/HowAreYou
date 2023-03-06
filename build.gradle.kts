// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
//    ext {
//        version_accompanist = "0.28.0"
//        version_appcompat = "1.6.0"
//        version_android_test_monitor = "1.5.0"
//        version_biometric = "1.2.0-alpha05"
//        version_compose = "1.3.1"
//        version_compose_activity = "1.6.1"
//        version_constraint_layout = "2.1.4"
//        version_core = "1.9.0"
//        version_coroutine = "1.4.2"
//        version_coroutine_android = "1.6.4"
//        version_crashlytics_plugin = "2.9.4"
//        version_data_store = "1.1.0-dev01"
//        version_date_picker = "0.9.0"
//        version_dagger_hilt = "2.44"
//        version_dagger_hilt_navigation = "1.0.0"
//        version_dagger_hilt_compiler = "1.0.0"
//        version_desugar_jdk = "1.2.2"
//        version_espresso_core = "3.5.0"
//        version_fragment = "2.5.1"
//        version_firebase = "20.1.0"
//        version_firebase_auth = "21.1.0"
//        version_firebase_auth_services = "20.4.1"
//        version_firebase_bom = "31.2.2"
//        version_firebase_ui = "7.2.0"
//        version_google_services = "4.3.15"
//        version_gradle = "7.4.2"
//        version_gradle_navigation = "2.5.2"
//        version_glide = "4.14.2"
//        version_glide_сompose = "1.0.0-alpha.1"
//        version_junit = "4.13.2"
//        version_junit_ext = "1.1.4"
//        version_kotlin = "1.7.20"
//        version_kotlin_compiler_ext = "1.3.2"
//        version_kotlin_coroutines = "1.4.1"
//        version_landscapist_glide = "2.1.2"
//        version_leak_canary = "2.9.1"
//        version_lifecycle = "2.6.0-alpha05"
//        version_lifecycle_extensions = "2.2.0"
//        version_macro_benchmark = "1.1.1"
//        version_material = "1.8.0"
//        version_navigation = "2.5.3"
//        version_orbit_mvi = "4.5.0"
//        version_profile_installer = "1.3.0-beta01"
//        version_room = "2.5.0"
//        version_splash_screen = "1.0.0"
//        version_shimmer = "1.0.3"
//        version_timber = "5.0.1"
//        version_uiautomator = "2.2.0"
//    }

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${libs.versions.gradle.get()}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.general.get()}")
        classpath("com.google.gms:google-services:${libs.versions.google.services.get()}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${libs.versions.firebase.crashlytics.get()}")
    }
}

plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.lib) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
}