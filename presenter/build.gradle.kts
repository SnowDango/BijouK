plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.snowdango.bijouk.presenter"
    compileSdk = libs.versions.sdk.target.get().toInt()
    version = libs.versions.version

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    lint {
        textReport = true
    }
}

dependencies {
    implementation(project(":features:device"))
    implementation(project(":features:setting"))
    implementation(project(":features:search"))
    implementation(project(":features:queue"))
    implementation(project(":features:artist"))
    implementation(project(":features:playlist"))
    implementation(project(":features:album"))
    implementation(project(":ui"))
    implementation(project(":model"))
    implementation(project(":infla"))
    implementation(libs.bundles.android.base)
    implementation(libs.androidx.splash)
    implementation(libs.androidx.media)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.android.compose)
    implementation(libs.material.icon)
    implementation(libs.bundles.coroutine)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.aboutlibraries)
    implementation(libs.kotlinx.serialization)
    testImplementation(libs.bundles.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.debug)
    testImplementation(kotlin("test"))
}