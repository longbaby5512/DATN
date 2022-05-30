import org.gradle.api.JavaVersion

object Versions {
    const val KOTLIN = "1.6.21"
    const val ANDROID_PLUGIN = "7.2.1"
    const val CMAKE_VERSION = "3.18.1"

    object KotlinX {
        const val KOTLINX = "1.6.1"
    }

    object Apps {
        const val COMPILE_SDK = 32
        const val MIN_SDK = 21
        const val TARGET_SDK = 32
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0"
    }

    object AndroidX {
        const val SAFE_ARGS = "2.5.0-rc01"

        const val CORE_KTX = "1.7.0"
        const val APPCOMPAT = "1.4.1"
        const val MATERIAL = "1.7.0-alpha02"
        const val CONSTRAINT_LAYOUT = "2.1.4"
        const val NAVIGATION = "2.4.2"
        const val RECYCLERVIEW = "1.2.1"
        const val FRAGMENT = "1.4.1"
    }

    object Google {
        const val MATERIAL = "1.7.0-alpha02"
        const val DAGGER_HILT = "2.41"
    }

    object Test {
        const val JUNIT = "4.13.2"

    }

    object AndroidTest {
        const val JUNIT = "1.1.3"
        const val ESPRESSO = "3.4.0"
    }

    const val LOTTIE = "5.0.3"
    const val TIMBER = "5.0.1"
}