plugins {
    id(Libs.Plugins.LIBRARY)
    id(Libs.Plugins.KOTLIN_ANDROID)
    kotlin("kapt")
    id(Libs.Plugins.DAGGER_HILT)
}

android {
    compileSdk = Versions.Apps.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.Apps.MIN_SDK
        targetSdk = Versions.Apps.TARGET_SDK

        testInstrumentationRunner = Configs.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
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

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.APPCOMPAT)
    implementation(Libs.Google.MATERIAL)
    implementation(Libs.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libs.AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(Libs.AndroidX.NAVIGATION_UI_KTX)
    implementation(project(mapOf("path" to ":base")))
    implementation(project(mapOf("path" to ":chaotic")))
    implementation(project(mapOf("path" to ":common")))
    testImplementation(Libs.Test.JUNIT)
    androidTestImplementation(Libs.AndroidTest.JUNIT)
    androidTestImplementation(Libs.AndroidTest.ESPRESSO_CORE)

    //Timber
    implementation(Libs.TIMBER)

    // Dagger Hilt
    implementation(Libs.Google.DaggerHilt.HILT_ANDROID)
    kapt(Libs.Google.DaggerHilt.HILT_ANDROID_COMPILER)
}

kapt {
    correctErrorTypes = Configs.CORRECT_ERROR_TYPE
}