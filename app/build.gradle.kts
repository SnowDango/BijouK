import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.deploygate)
}

android {
    namespace = "com.snowdango.bijouk"
    compileSdk = libs.versions.sdk.target.get().toInt()

    val versionNameBase = libs.versions.version.get()

    defaultConfig {
        applicationId = "com.snowdango.bijouk"
        minSdk = libs.versions.sdk.min.get().toInt()
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

deploygate {
    val properties = readProperties(file("../local.properties"))
    appOwnerName = properties.getProperty("deploygate.user")
    apiToken = properties.getProperty("deploygate.token")
    deployments {
        create("release") {
            sourceFile = file("build/outputs/apk/release/app-release.apk")
        }
        create("debug") {
            sourceFile = file("build/outputs/apk/debug/app-debug.apk")
        }
    }
}

dependencies {
    implementation(project(":features:device"))
    implementation(project(":features:nowPlay"))
    implementation(project(":features:setting"))
    implementation(project(":features:artist"))
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
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.bundles.test)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.debug)
}

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}