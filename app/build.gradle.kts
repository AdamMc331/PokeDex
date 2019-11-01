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
    implementation(Dependencies.appCompat)
    implementation(Dependencies.ktxCore)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.materialDesign)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.lifecycleExtensions)
    implementation(Dependencies.viewModelKtx)
    implementation(Dependencies.okhttpLogging)
    implementation(Dependencies.materialDesignSpecs)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.coroutinesAdapter)
    implementation(Dependencies.swipeRefreshLayout)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.navigationUiKtx)
    implementation(Dependencies.gson)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.mockito)
    testImplementation(Dependencies.archTesting)
    testImplementation(Dependencies.coroutinesTest)
    androidTestImplementation(Dependencies.androidXTestCore)
    androidTestImplementation(Dependencies.androixTestRunner)
    androidTestImplementation(Dependencies.androidXTestRules)
    androidTestImplementation(Dependencies.androidXJunitExtension)
    androidTestImplementation(Dependencies.espressoCore)
    debugImplementation(Dependencies.fragmentTesting) {
        exclude("androidx.test", "core")
    }
    androidTestImplementation(Dependencies.mockwebserver)
}