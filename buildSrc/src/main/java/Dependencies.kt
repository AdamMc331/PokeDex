import org.gradle.api.artifacts.dsl.DependencyHandler

sealed class DependencyConfig(val name: String) {
    class Implementation(name: String) : DependencyConfig(name)
    class TestImplementation(name: String) : DependencyConfig(name)
    class AndroidTestImplementation(name: String) : DependencyConfig(name)
    class Kapt(name: String) : DependencyConfig(name)
    class AnnotationProcessor(name: String) : DependencyConfig(name)
}

object Dependencies {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val junit = "junit:junit:${Versions.junit}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room}"
    const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val materialDesignSpecs =
        "com.androidessence:materialdesignspecs:${Versions.materialDesignSpecs}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val archTesting = "androidx.arch.core:core-testing:${Versions.archTesting}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val androidXTestCore = "androidx.test:core:${Versions.androidXTest}"
    const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
    const val androidXJunitExtension = "androidx.test.ext:junit:${Versions.androidXJunit}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragments}"
    const val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

    val imageLoaderDependencies = listOf(
        DependencyConfig.Implementation(appCompat),
        DependencyConfig.Implementation(coil)
    )

    val databaseDependencies = listOf(
        DependencyConfig.Implementation(appCompat),
        DependencyConfig.Implementation(ktxCore),
        DependencyConfig.Implementation(roomRuntime),
        DependencyConfig.Implementation(roomKtx),
        DependencyConfig.Implementation(gson),
        DependencyConfig.Kapt(roomCompiler),
        DependencyConfig.AnnotationProcessor(roomCompiler),
        DependencyConfig.TestImplementation(junit),
        DependencyConfig.AndroidTestImplementation(roomTesting),
        DependencyConfig.AndroidTestImplementation(androidXTestRunner),
        DependencyConfig.AndroidTestImplementation(espressoCore)
    )

    val appDependencies = listOf(
        DependencyConfig.Implementation(appCompat),
        DependencyConfig.Implementation(ktxCore),
        DependencyConfig.Implementation(constraintLayout),
        DependencyConfig.Implementation(materialDesign),
        DependencyConfig.Implementation(retrofit),
        DependencyConfig.Implementation(moshiConverter),
        DependencyConfig.Implementation(lifecycleExtensions),
        DependencyConfig.Implementation(viewModelKtx),
        DependencyConfig.Implementation(okhttpLogging),
        DependencyConfig.Implementation(materialDesignSpecs),
        DependencyConfig.Implementation(coroutinesCore),
        DependencyConfig.Implementation(coroutinesAndroid),
        DependencyConfig.Implementation(coroutinesAdapter),
        DependencyConfig.Implementation(swipeRefreshLayout),
        DependencyConfig.Implementation(navigationFragmentKtx),
        DependencyConfig.Implementation(navigationUiKtx),
        DependencyConfig.TestImplementation(junit),
        DependencyConfig.TestImplementation(mockito),
        DependencyConfig.TestImplementation(archTesting),
        DependencyConfig.TestImplementation(coroutinesTest),
        DependencyConfig.AndroidTestImplementation(androidXTestCore),
        DependencyConfig.AndroidTestImplementation(androidXTestRunner),
        DependencyConfig.AndroidTestImplementation(androidXTestRules),
        DependencyConfig.AndroidTestImplementation(androidXJunitExtension),
        DependencyConfig.AndroidTestImplementation(espressoCore),
        DependencyConfig.AndroidTestImplementation(mockwebserver)
    )
}

fun DependencyHandler.addDependencies(dependencies: List<DependencyConfig>) {
    dependencies.forEach {
        when (it) {
            is DependencyConfig.Implementation -> add(
                "implementation",
                it.name
            )
            is DependencyConfig.TestImplementation -> add(
                "testImplementation",
                it.name
            )
            is DependencyConfig.AndroidTestImplementation -> add(
                "androidTestImplementation",
                it.name
            )
            is DependencyConfig.Kapt -> add(
                "kapt",
                it.name
            )
            is DependencyConfig.AnnotationProcessor -> add(
                "annotationProcessor",
                it.name
            )
        }
    }
}