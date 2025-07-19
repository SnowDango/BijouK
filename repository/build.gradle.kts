plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.snowdango.bijouk.repository"
        compileSdk = libs.versions.sdk.target.get().toInt()
        minSdk = libs.versions.sdk.min.get().toInt()
        version = libs.versions.version

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    cocoapods {
        version = libs.versions.version.get()
        summary = "Bijouk Repository Module"
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries {
                framework {
                    baseName = "repositoryKit"
                }
            }
        }
        ios.deploymentTarget = "11.0"
    }

    // Source set declarations.
    // Declaring a target automatically creates a source set with the same name. By default, the
    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
    // common to share sources between related targets.
    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":domain"))
                implementation(libs.kotlin.stdlib)

                // koin
                implementation(libs.koin.core)
                implementation(libs.koin.compose)

                // kmlogger
                implementation(libs.kmlogger)

                // room
                implementation(libs.room.runtime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }

    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}