plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id(Libs.Plugins.GOOGLE_SERVICES)
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
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Configs.MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }
    compileOptions {
        sourceCompatibility = Configs.JAVA_VERSION
        targetCompatibility = Configs.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = Configs.JAVA_VERSION.toString()
    }
}

dependencies {

    implementation(Libs.AndroidX.CORE_KTX)
    implementation(Libs.AndroidX.APPCOMPAT)
    implementation(Libs.Google.MATERIAL)
    testImplementation(Libs.Test.JUNIT)
    androidTestImplementation(Libs.AndroidTest.JUNIT)
    androidTestImplementation(Libs.AndroidTest.ESPRESSO_CORE)

    implementation(Libs.AndroidX.SECURITY_CRYPTO)
    implementation(Libs.SECURE_PREFERENCES)

    // Moshi
    implementation(Libs.Google.GSON)

    implementation(Libs.Squareup.LOGGING_INTERCEPTOR)

    // Dagger Hilt
    implementation(Libs.Google.HILT)
    kapt(Libs.Google.HILT_COMPILER)

    // Firebase
    implementation(platform(Libs.Google.Firebase.BOM))
    implementation(Libs.Google.Firebase.DATABASE)
    implementation(Libs.Google.Firebase.COMMON)

    // Timber
    implementation(Libs.TIMBER)
}

kapt {
    correctErrorTypes = Configs.CORRECT_ERROR_TYPE
}