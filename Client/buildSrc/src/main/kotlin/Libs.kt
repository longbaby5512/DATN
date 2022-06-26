
object Libs {
    object Plugins {
        const val DAGGER_HILT = "dagger.hilt.android.plugin"
        const val GOOGLE_SERVICES = "com.google.gms.google-services"
    }
    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.AndroidX.CORE_KTX}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.AndroidX.APPCOMPAT}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.CONSTRAINT_LAYOUT}"
        const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.NAVIGATION}"
        const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.NAVIGATION}"
        const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.AndroidX.RECYCLERVIEW}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.AndroidX.FRAGMENT}"
        const val SECURITY_CRYPTO = "androidx.security:security-crypto-ktx:${Versions.AndroidX.SECURITY_CRYPTO}"
        const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val SAVED_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.AndroidX.LIFECYCLE}"
    }



    object Size {
        const val SSP = "com.intuit.ssp:ssp-android:${Versions.Size.SSP}"
        const val SDP = "com.intuit.sdp:sdp-android:${Versions.Size.SDP}"
    }


    object KotlinX {
        const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KotlinX.KOTLINX}"
        const val KOTLINX_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KotlinX.KOTLINX}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"
        const val DAGGER = "com.google.dagger:dagger:${Versions.Google.DAGGER}"
        const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.Google.DAGGER}"
        const val HILT = "com.google.dagger:hilt-android:${Versions.Google.DAGGER}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.Google.DAGGER}"
        const val GSON = "com.google.code.gson:gson:${Versions.Google.GSON}"

        object Firebase {
            const val BOM = "com.google.firebase:firebase-bom:${Versions.Google.Firebase.BOM}"
            const val DATABASE = "com.google.firebase:firebase-database-ktx"
            const val COMMON = "com.google.firebase:firebase-common-ktx"
        }
    }

    const val SECURE_PREFERENCES = "com.scottyab:secure-preferences-lib:0.1.7"

    object Squareup {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Retrofit.RETROFIT}"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit.RETROFIT}"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.Retrofit.LOGGING_INTERCEPTOR}"
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.Test.JUNIT}"
    }

    object AndroidTest {
        const val JUNIT = "androidx.test.ext:junit-ktx:${Versions.AndroidTest.JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.ESPRESSO}"
    }

    const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val XXPERMISSION = "com.github.getActivity:XXPermissions:${Versions.XXPERMISSION}"
}