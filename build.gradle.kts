// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
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