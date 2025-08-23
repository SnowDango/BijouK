pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "BijouK"

include(":app")
include(":presenter")
include(":features:device")
include(":features:setting")
include(":features:artist")
include(":features:queue")
include(":features:search")
include(":features:playlist")
include(":repository")
include(":domain")
include(":model")
include(":ui")
include(":analytics")
include(":infla")
include(":features:album")
