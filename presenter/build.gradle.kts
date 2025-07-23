plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aboutLibraries)
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
            isMinifyEnabled = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    lint {
        textReport = true
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(libs.bundles.android.base)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.android.compose)
    implementation(libs.bundles.coroutine)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.aboutlibraries)
    testImplementation(libs.bundles.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.debug)
    testImplementation(kotlin("test"))
}