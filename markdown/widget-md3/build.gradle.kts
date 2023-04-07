import com.vanniktech.maven.publish.KotlinMultiplatform

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.vanniktech.maven.publish.base")
}

kotlin {
    android {
        publishLibraryVariants("debug", "release")
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = Versions.Java.jvmTarget
        }
    }
    ios()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    js(IR) {
        browser()
        nodejs()
    }
    sourceSets {
        commonMain {
            dependencies {
                api(compose.material3)
                api(projects.markdown.contentAnnotatedString)
            }
        }
    }
}

android {
    namespace = "io.github.qdsfdhvh.markdown.widget.m3"
    compileSdk = Versions.Android.compile
    defaultConfig {
        minSdk = Versions.Android.min
    }
    compileOptions {
        sourceCompatibility = Versions.Java.source
        targetCompatibility = Versions.Java.target
    }
}

mavenPublishing {
    @Suppress("UnstableApiUsage")
    configure(KotlinMultiplatform())
}
