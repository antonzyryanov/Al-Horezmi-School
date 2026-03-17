rootProject.name = "Al Horezmi School"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://artifactory.appodeal.com/appodeal")
        maven(url = "https://artifactory.bidmachine.io/bidmachine")
        maven(url = "https://android-sdk.is.com")
        maven(url = "https://artifact.bytedance.com/repository/pangle")
    }
}

include(":composeApp")
