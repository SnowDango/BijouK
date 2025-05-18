plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.snowdango.bijouk.model"
    compileSdk = 35

    defaultConfig {
        minSdk = 30

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
    lint {
        textReport = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":domain2"))
    implementation(project(":repository"))
    implementation(libs.aboutlibraries.core)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.coroutine)
    testImplementation(libs.bundles.test)
    testImplementation(kotlin("test"))
}