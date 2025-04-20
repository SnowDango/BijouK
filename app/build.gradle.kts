plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.aboutLibraries)
}

android {
    namespace = "com.snowdango.bijouk"
    compileSdk = 35

    val versionNameBase = "0.0.1"

    defaultConfig {
        applicationId = "com.snowdango.bijouk"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = versionNameBase
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "VERSION_NAME", "\"$versionNameBase\"")
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "VERSION_NAME", "\"$versionNameBase-debug\"")
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
        buildConfig = true
    }
    lint {
        textReport = true
    }
}

aboutLibraries {
    offlineMode = false
    android {
        registerAndroidTasks = true
    }
}

dependencies {
    implementation(project(":features:device"))
    implementation(project(":features:nowPlay"))
    implementation(project(":features:setting"))
    implementation(project(":repository"))
    implementation(project(":domain"))
    implementation(project(":model"))
    implementation(project(":ui"))
    implementation(libs.androidx.splash)
    implementation(libs.bundles.android.base)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.android.compose)
    implementation(libs.aboutlibraries.core)
    implementation(libs.material.icon)
    implementation(libs.bundles.coroutine)
    implementation(libs.material.kolor)
    implementation(libs.bundles.koin)
    implementation(libs.kotlinx.serialization)
    testImplementation(libs.bundles.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.debug)
}