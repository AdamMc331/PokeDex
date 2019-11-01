import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("androidx.core:core-ktx:${Versions.ktxCore}")
    implementation("androidx.room:room-runtime:${Versions.room}")
    implementation("androidx.room:room-ktx:${Versions.room}")
    implementation("com.google.code.gson:gson:${Versions.gson}")
    annotationProcessor("androidx.room:room-compiler:${Versions.room}")
    kapt("androidx.room:room-compiler:${Versions.room}")
    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.room:room-testing:${Versions.room}")
    androidTestImplementation("androidx.test:runner:${Versions.androidXTest}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")
}