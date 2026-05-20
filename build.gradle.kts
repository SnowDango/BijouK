import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.lint.AndroidLintTask
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlin.plugin)
        classpath(libs.detekt.plugin)
        classpath(libs.deploygate.plugin)
        classpath(libs.openapi.plugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.android.lint) apply false
}

val reportMerge = tasks.register<ReportMergeTask>("reportMerge") {
    output = rootProject.file("./reports/detekt.xml")
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
        config = files("${rootProject.projectDir}/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        ignoreFailures = true
        basePath = file("$rootDir/../").absolutePath
    }

    tasks.withType<AndroidLintTask> {
        finalizedBy("detekt")
    }
    tasks.withType<Detekt> {
        finalizedBy(reportMerge)
    }

    reportMerge.configure {
        input.from(tasks.withType(Detekt::class).map { it.xmlReportFile })
    }

    dependencies {
        detektPlugins(rootProject.libs.bundles.detekt)
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}