import org.gradle.api.JavaVersion

object Configs {
    val JAVA_VERSION = JavaVersion.VERSION_1_8
    const val VIEW_BINDING = true
    const val MINIFY_ENABLED = true
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val CMAKE_FILE_PATH = "src/main/cpp/CMakeLists.txt"
    const val APPLICATION_ID = "com.karry.chatapp"
    const val CORRECT_ERROR_TYPE = true
}

//testInstrumentationRunner