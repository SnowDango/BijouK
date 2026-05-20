plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {

    androidLibrary {
        namespace = "com.snowdango.bijouk.model"
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
        summary = "Bijouk Model Module"
        listOf(
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries {
                framework {
                    baseName = "modelKit"
                }
            }
        }
        ios.deploymentTarget = "11.0"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":analytics"))
                implementation(project(":api"))
                implementation(project(":domain"))
                implementation(project(":repository"))
                implementation(libs.kotlin.stdlib)

                // koin
                implementation(libs.koin.core)
                implementation(libs.koin.compose)

                // paging
                implementation(libs.paging.common)

                // coroutines
                implementation(libs.kotlinx.coroutine.core)

                // kmlogger
                implementation(libs.kmlogger)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
            }
        }

        iosMain {
            dependencies {
            }
        }
    }

    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}