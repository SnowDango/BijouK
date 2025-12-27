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
    alias(libs.plugins.openapi)
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

    signingConfigs {
        val properties = readProperties(file("../local.properties"))
        create("release") {
            storeFile = file("../release.keystore")
            storePassword = properties.getProperty("release.storepass")
            keyAlias = "snowdango"
            keyPassword = properties.getProperty("release.keypass")
        }
        getByName("debug") {
            storeFile = file("../debug.keystore")
            storePassword = properties.getProperty("debug.storepass")
            keyAlias = "snowdango"
            keyPassword = properties.getProperty("debug.keypass")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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

openApiGenerate {
    groupId = "com.snowdango.bijouk.api"
    version = libs.versions.version.get()
    additionalProperties
    generatorName = "kotlin"
    validateSpec = false
    inputSpec = "${rootDir}/cider.json"
    outputDir = "${rootDir}/api"
    library = "multiplatform"
    apiPackage = "com.snowdango.bijouk.api"
    invokerPackage = "com.snowdango.bijouk.api.invoker"
    modelPackage = "com.snowdango.bijouk.api.model"
    configOptions = mapOf(
        "dateLibrary" to "kotlinx-datetime"
    )
    additionalProperties = mapOf(
        "useCoroutines" to "true",
        "enumPropertyNaming" to "UPPERCASE",
        "generateOneOfAnyOfWrappers" to "true"
    )
}
openApiValidate {
    inputSpec = "${rootDir}/cider.json"
}

dependencies {
    implementation(project(":presenter"))
    implementation(project(":repository"))
    implementation(project(":domain"))
    implementation(project(":model"))
    implementation(project(":ui"))
    implementation(project(":infla"))
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