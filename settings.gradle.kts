pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    includeBuild("build-logic")
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}

rootProject.name = "compose-markdown"

include(
    ":markdown",
    ":app:common",
    ":app:android",
    ":app:desktop",
    ":app:ios-combine",
    ":app:macos",
    ":app:web",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
