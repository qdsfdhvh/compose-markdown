

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(projects.app.common)
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}

compose.experimental {
    web.application {
    }
}
