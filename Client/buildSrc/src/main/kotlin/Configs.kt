import org.gradle.api.JavaVersion

object Configs {
    val JAVA_VERSION = JavaVersion.VERSION_11
    const val VIEW_BINDING = true
    const val MINIFY_ENABLED = true
    const val MULTI_DEX_ENABLED = true
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val APPLICATION_ID = "com.karry.chatapp"
    const val CORRECT_ERROR_TYPE = true
}

//testInstrumentationRunner