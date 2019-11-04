import org.gradle.api.artifacts.dsl.DependencyHandler

sealed class DependencyConfig(val name: String) {
    class Implementation(name: String) : DependencyConfig(name)
    class TestImplementation(name: String) : DependencyConfig(name)
    class AndroidTestImplementation(name: String) : DependencyConfig(name)
    class Kapt(name: String) : DependencyConfig(name)
    class AnnotationProcessor(name: String) : DependencyConfig(name)
}

object Dependencies {
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
    private const val coil = "io.coil-kt:coil:${Versions.coil}"
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    private const val junit = "junit:junit:${Versions.junit}"
    private const val roomTesting = "androidx.room:room-testing:${Versions.room}"
    private const val androidXTestRunner = "androidx.test:runner:${Versions.androidXTest}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    private const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    private const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    private const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    private const val materialDesignSpecs =
        "com.androidessence:materialdesignspecs:${Versions.materialDesignSpecs}"
    private const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    private const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"
    private const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    private const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"
    private const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    private const val archTesting = "androidx.arch.core:core-testing:${Versions.archTesting}"
    private const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    private const val androidXTestCore = "androidx.test:core:${Versions.androidXTest}"
    private const val androidXTestRules = "androidx.test:rules:${Versions.androidXTest}"
    private const val androidXJunitExtension = "androidx.test.ext:junit:${Versions.androidXJunit}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragments}"
    private const val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

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
        DependencyConfig.Implementation(lifecycleExtensions),
        DependencyConfig.Implementation(viewModelKtx),
        DependencyConfig.Implementation(materialDesignSpecs),
        DependencyConfig.Implementation(coroutinesCore),
        DependencyConfig.Implementation(coroutinesAndroid),
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
    
    val networkDependencies = listOf(
        DependencyConfig.Implementation(appCompat),
        DependencyConfig.Implementation(moshiConverter),
        DependencyConfig.Implementation(retrofit),
        DependencyConfig.Implementation(coroutinesAdapter),
        DependencyConfig.Implementation(okhttpLogging),
        DependencyConfig.TestImplementation(junit),
        DependencyConfig.AndroidTestImplementation(androidXTestRunner),
        DependencyConfig.AndroidTestImplementation(espressoCore)
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