import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val kotlin_version by extra("1.3.61")
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0-alpha09")
        classpath(kotlin("gradle-plugin", version = Versions.kotlin))
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}")
        classpath("com.novoda:gradle-static-analysis-plugin:${Versions.staticAnalysis}")
        classpath("com.github.ben-manes:gradle-versions-plugin:${Versions.versionsPlugin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")
        classpath("com.apollographql.apollo:apollo-gradle-plugin:${Versions.apolloGraphql}")
        // Using a fork: https://github.com/arturdm/jacoco-android-gradle-plugin/pull/75#issuecomment-565222643
        classpath("com.hiya:jacoco-android:${Versions.jacoco}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "com.novoda.static-analysis")

    ktlint {
        version.set("0.36.0")
        android.set(true)
        enableExperimentalRules.set(true)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
        additionalEditorconfigFile.set(file("${project.projectDir}/.editorConfig"))
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

afterEvaluate {
    // We install the hook at the first occasion
    tasks["clean"].dependsOn(tasks.getByName("addKtlintFormatGitPreCommitHook"))
}

/**
 * This is a special task that allows us to pass a flag to avoid any tasks with Lint in the name.
 *
 * To use, you can call `./gradlew build -PnoLint` from the command line.
 *
 * https://kousenit.org/2016/04/20/excluding-gradle-tasks-with-a-name-pattern/
 */
gradle.taskGraph.whenReady {
    if (project.hasProperty("noLint")) {
        this.allTasks.filter {
            it.name.contains("lint")
        }.forEach {
            it.enabled = false
        }
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint").version(Versions.ktlint)
    id("org.jlleitschuh.gradle.ktlint-idea").version(Versions.ktlint)
    id("com.github.ben-manes.versions").version(Versions.versionsPlugin)
}

tasks.withType<DependencyUpdatesTask> {
    checkForGradleUpdate = false

    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(this.candidate.version) && !isNonStable(this.currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
}

fun isNonStable(version: String): Boolean {
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = regex.matches(version)
    return isStable.not()
}