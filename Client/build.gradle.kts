buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.SAFE_ARGS}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Google.DAGGER}")
        classpath("com.google.gms:google-services:${Versions.Google.GOOGLE_SERVICES}")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.0-beta04" apply false
    id("com.android.library") version "7.3.0-beta04" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}