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

tasks.register<ReportMergeTask>("reportMerge") {
    output = rootProject.file("../misc-reports/detekt.xml")
}

subprojects {
    apply(
        plugin = "io.gitlab.arturbosch.detekt"
    )

    detekt {
        autoCorrect = true
        parallel = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        ignoreFailures = true
        basePath = file("$rootDir/../").absolutePath
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
    }

    tasks.withType(Detekt::class.java).configureEach {
        finalizedBy("reportMerge")
    }

    task<ReportMergeTask>("reportMerge") {
        input.from(tasks.withType(Detekt::class.java).map { it.xmlReportFile })
    }
}