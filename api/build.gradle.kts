import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.spotless)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += "-Xexpect-actual-classes"
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    jvm {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs += "-Xexpect-actual-classes"
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    // iosX64()
    // iosArm64()
    // iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.ktor.core)
        }
    }
}

android {
    namespace = "app.mihon.extension"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}

spotless {
    kotlin {
        target("**/*.kt", "**/*.kts")
        targetExclude("**/build/**/*.kt")
        ktlint(libs.ktlint.cli.get().version)
            .editorConfigOverride(
                mapOf(
                    "ktlint_standard_discouraged-comment-location" to "disabled",
                ),
            )
        trimTrailingWhitespace()
        endWithNewline()
    }
    format("misc") {
        target("**/.gitignore", "**/*.xml")
        targetExclude("**/build/**/*.xml")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

mavenPublishing {
    val isSnapshot = System.getenv("SNAPSHOT").toBoolean()

    val version = "0.1.0"

    coordinates(
        groupId = "app.mihon",
        artifactId = "extension-api",
        version = if (isSnapshot) "$version-SNAPSHOT" else version,
    )

    pom {
        name.set("Mihon Extension API")
        description.set("Extension API for Mihon an open source Manga reader app")
        url.set("https://github.com/mihonapp/extension-api")
        inceptionYear.set("2024")

        licenses {
            license {
                name.set("Mozilla Public License Version 2.0")
                url.set("https://www.mozilla.org/en-US/MPL/2.0")
                distribution.set("repo")
            }
        }

        organization {
            organization {
                name.set("The Mihon Open Source Project")
                url.set("https://github.com/mihonapp")
            }
        }

        developers {
            developer {
                id.set("antsylich")
                name.set("AntsyLich")
                url.set("https://github.com/AntsyLich")
            }

            developer {
                id.set("syer10")
                name.set("Syer10")
                url.set("https://github.com/Syer10")
            }
        }

        scm {
            url.set("https://github.com/mihonapp/extension-api.kt")
            connection.set("scm:git:git://github.com/mihonapp/extension-api.git")
        }
    }

    signAllPublications()
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = isSnapshot)
}
