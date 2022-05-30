
object Libs {
    object BuildScripts {
        const val SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.AndroidX.SAFE_ARGS}"
        const val DAGGER_HILT = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Google.DAGGER_HILT}"
    }
    object Plugins {
        const val APPLICATION = "com.android.application"
        const val LIBRARY = "com.android.library"
        const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
        const val KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
        const val DAGGER_HILT = "dagger.hilt.android.plugin"
    }
    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.AndroidX.CORE_KTX}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.AndroidX.APPCOMPAT}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.CONSTRAINT_LAYOUT}"
        const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.NAVIGATION}"
        const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.NAVIGATION}"
        const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.AndroidX.RECYCLERVIEW}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.AndroidX.FRAGMENT}"
    }



    object KotlinX {
        const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KotlinX.KOTLINX}"
        const val KOTLINX_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KotlinX.KOTLINX}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"

        object DaggerHilt {
            const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.Google.DAGGER_HILT}"
            const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.Google.DAGGER_HILT}"
        }
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.Test.JUNIT}"
    }

    object AndroidTest {
        const val JUNIT = "androidx.test.ext:junit:${Versions.AndroidTest.JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.ESPRESSO}"
    }

    const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
}