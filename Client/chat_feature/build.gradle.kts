plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id(Libs.Plugins.DAGGER_HILT)
}

android {
    compileSdk = Versions.Apps.COMPILE_SDK
    buildToolsVersion = Versions.Apps.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = Versions.Apps.MIN_SDK
        targetSdk = Versions.Apps.TARGET_SDK
        multiDexEnabled = Configs.MULTI_DEX_ENABLED

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
        dataBinding = Configs.VIEW_BINDING
    }
}

dependencies {
    implementation(project(mapOf("path" to ":base")))
    implementation(project(mapOf("path" to ":chaotic")))
    implementation(project(mapOf("path" to ":common")))
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.APPCOMPAT)
    implementation(Libs.Google.MATERIAL)
    implementation(Libs.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libs.AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(Libs.AndroidX.NAVIGATION_UI_KTX)

    testImplementation(Libs.Test.JUNIT)
    androidTestImplementation(Libs.AndroidTest.JUNIT)
    androidTestImplementation(Libs.AndroidTest.ESPRESSO_CORE)

    // Coroutines
    implementation(Libs.KotlinX.KOTLINX_COROUTINES_CORE)
    implementation(Libs.KotlinX.KOTLINX_COROUTINES_ANDROID)

    //Timber
    implementation(Libs.TIMBER)

    // Dagger Hilt
    implementation(Libs.Google.HILT)
    kapt(Libs.Google.HILT_COMPILER)

    // XXPermission
    implementation(Libs.XXPERMISSION)

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