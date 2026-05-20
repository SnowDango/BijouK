plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.aboutLibraries)
}

android {
    namespace = "com.snowdango.bijouk.features.setting"
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
    implementation(project(":ui"))
    implementation(project(":model"))
    implementation(libs.bundles.android.base)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.android.compose)
    implementation(libs.bundles.settings.compose)
    implementation(libs.material.icon)
    implementation(libs.bundles.aboutlibraries)
    implementation(libs.markdown)
    implementation(libs.bundles.koin)
    testImplementation(libs.bundles.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.debug)
    testImplementation(kotlin("test"))
}