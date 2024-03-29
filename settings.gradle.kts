import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
//        maven {
//            url 'https://s01.oss.sonatype.org/content/repositories/snapshots'
//        }
    }
}

rootProject.name = "HowAreYou"
include(":app", ":data", ":domain", ":macrobenchmark")
