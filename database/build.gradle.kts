import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.hiya.jacoco-android")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mutableMapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        // Adds exported schema location as test app assets.
        val schemaFiles = files("$projectDir/schemas")
        val initialAndroidTestDirs = getByName("androidTest").assets.srcDirs
        getByName("androidTest").assets.setSrcDirs(initialAndroidTestDirs + schemaFiles)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(project(":core"))

    addDependencies(Dependencies.databaseDependencies)
}

configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    config = files("${rootProject.projectDir}/config/detekt/detekt.yml")
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.5"
}

configure<com.hiya.plugins.JacocoAndroidUnitTestReportExtension> {
    csv.enabled(false)
    html.enabled(true)
    xml.enabled(false)

    excludes = this.excludes + listOf()
}