pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}
rootProject.name = "Chat App"
include(":app", ":chaotic", ":base", ":account_feature", ":common")
include(":data")
include(":domain")
include(":remote")
include(":chat_feature")
