
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id(Libs.Plugins.DAGGER_HILT)

}

android {
    compileSdk = Versions.Apps.COMPILE_SDK

    defaultConfig {
        applicationId = Configs.APPLICATION_ID
        minSdk = Versions.Apps.MIN_SDK
        targetSdk = Versions.Apps.TARGET_SDK
        versionCode = Versions.Apps.VERSION_CODE
        versionName = Versions.Apps.VERSION_NAME
        multiDexEnabled = Configs.MULTI_DEX_ENABLED

        testInstrumentationRunner = Configs.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = Configs.MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = Configs.JAVA_VERSION
        targetCompatibility = Configs.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = Configs.JAVA_VERSION.toString()
    }
    buildFeatures {
        viewBinding = Configs.VIEW_BINDING
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":account_feature"))
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":remote"))
    implementation(project(":domain"))
    implementation(project(":chat_feature"))

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.APPCOMPAT)
    implementation(Libs.Google.MATERIAL)
    implementation(Libs.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libs.AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(Libs.AndroidX.NAVIGATION_UI_KTX)


    testImplementation(Libs.Test.JUNIT)
    androidTestImplementation(Libs.AndroidTest.JUNIT)
    androidTestImplementation(Libs.AndroidTest.ESPRESSO_CORE)

    implementation(Libs.LOTTIE)
    implementation(Libs.Squareup.LOGGING_INTERCEPTOR)


    //Timber
    implementation(Libs.TIMBER)

    // Dagger Hilt
    implementation(Libs.Google.HILT)
    kapt(Libs.Google.HILT_COMPILER)

    // Retrofit
    implementation(Libs.Squareup.RETROFIT)
    implementation(Libs.Squareup.CONVERTER_GSON)
    implementation(Libs.Squareup.LOGGING_INTERCEPTOR)

    // SSP - SDP
    implementation(Libs.Size.SSP)
    implementation(Libs.Size.SDP)
}

kapt {
    correctErrorTypes = Configs.CORRECT_ERROR_TYPE
}

hilt {
    enableAggregatingTask = true
}