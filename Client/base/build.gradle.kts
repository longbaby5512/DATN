plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Versions.Apps.COMPILE_SDK
    buildToolsVersion = Versions.Apps.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = Versions.Apps.MIN_SDK
        targetSdk = Versions.Apps.TARGET_SDK


        multiDexEnabled = Configs.MULTI_DEX_ENABLED

        testInstrumentationRunner = Configs.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.APPCOMPAT)
    implementation(Libs.Google.MATERIAL)
    testImplementation(Libs.Test.JUNIT)
    androidTestImplementation(Libs.AndroidTest.JUNIT)
    androidTestImplementation(Libs.AndroidTest.ESPRESSO_CORE)

    implementation(Libs.AndroidX.RECYCLERVIEW)

    // JetPack
    implementation(Libs.AndroidX.FRAGMENT_KTX)

    // Coroutines
    implementation(Libs.KotlinX.KOTLINX_COROUTINES_CORE)
    implementation(Libs.KotlinX.KOTLINX_COROUTINES_ANDROID)

}