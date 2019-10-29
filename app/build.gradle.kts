import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        applicationId = "com.adammcneilly.pokedex"
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        versionCode = Config.appVersionCode
        versionName = Config.appVersionName
        testInstrumentationRunner = "com.adammcneilly.pokedex.MockTestRunner"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField(
                "Integer",
                "PORT",
                "8080"
            )
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    dataBinding {
        isEnabled = true
    }

    testOptions {
        animationsDisabled = true
    }

    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

repositories {
    maven("http://dl.bintray.com/androidessence/maven")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":image-loader"))
    implementation(project(":database"))
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("androidx.core:core-ktx:${Versions.ktxCore}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")
    implementation("com.google.android.material:material:${Versions.materialDesign}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}")
    implementation("com.androidessence:materialdesignspecs:${Versions.materialDesignSpecs}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")
    implementation("com.google.code.gson:gson:${Versions.gson}")
    testImplementation("junit:junit:${Versions.junit}")
    testImplementation("org.mockito:mockito-core:${Versions.mockito}")
    testImplementation("androidx.arch.core:core-testing:${Versions.archTesting}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")
    androidTestImplementation("androidx.test:core:${Versions.androidXTest}")
    androidTestImplementation("androidx.test:runner:${Versions.androidXTest}")
    androidTestImplementation("androidx.test:rules:${Versions.androidXTest}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidXJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso}")
    debugImplementation("androidx.fragment:fragment-testing:${Versions.fragments}") {
        exclude("androidx.test", "core")
    }
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${Versions.okhttp}")
}