import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.lint.AndroidLintTask

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlin.plugin)
        classpath(libs.detekt.plugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.detekt)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    plugins.withId("com.android.library") {
        extensions.configure<BaseExtension> {
            lintOptions {
                textReport = true
                textOutput("stdout")
                isAbortOnError = true
                isCheckDependencies = true
            }
        }
    }

    detekt {
        autoCorrect = true
        parallel = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        ignoreFailures = true
        basePath = file("$rootDir/../").absolutePath
    }

    tasks.withType<AndroidLintTask> {
        finalizedBy("detekt")
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
    }
}