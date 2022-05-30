buildscript {
    dependencies {
        classpath(Libs.BuildScripts.SAFE_ARGS)
        classpath(Libs.BuildScripts.DAGGER_HILT)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Libs.Plugins.APPLICATION).version(Versions.ANDROID_PLUGIN).apply(false)
    id(Libs.Plugins.LIBRARY).version(Versions.ANDROID_PLUGIN).apply(false)
    id(Libs.Plugins.KOTLIN_ANDROID).version(Versions.KOTLIN).apply(false)
    id(Libs.Plugins.KOTLIN_JVM).version(Versions.KOTLIN).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}