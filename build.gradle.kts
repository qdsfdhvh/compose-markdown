import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.composeJb) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.publish)
    id("build-logic") apply false
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.Java.jvmTarget
        }
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/")
            ktlint(libs.versions.ktlint.get().toString())
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            targetExclude("**/build/")
            ktlint(libs.versions.ktlint.get().toString())
        }
    }

    group = "io.github.qdsfdhvh"
    version = Versions.Project.version

    plugins.withId("com.vanniktech.maven.publish.base") {
        mavenPublishing {
            publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
            signAllPublications()
            @Suppress("UnstableApiUsage")
            pom {
                name.set("compose-markdown")
                description.set("Compose Markdown.")
                url.set("https://github.com/qdsfdhvh/compose-markdown")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("Seiko")
                        name.set("SeikoDes")
                        email.set("seiko_des@outlook.com")
                    }
                }
                scm {
                    url.set("https://github.com/qdsfdhvh/compose-markdown")
                    connection.set("scm:git:git://github.com/qdsfdhvh/compose-markdown.git")
                    developerConnection.set("scm:git:git://github.com/qdsfdhvh/compose-markdown.git")
                }
            }
        }
    }
}
