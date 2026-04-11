pluginManagement {
    val kotlinVersion = providers.gradleProperty("kotlinVersion").get()
    val fooJayVersion = providers.gradleProperty("fooJayVersion").get()
    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        // jdk toolchain resolver plugin for jdk auto download and setup
        id("org.gradle.toolchains.foojay-resolver-convention") version fooJayVersion
    }

    val privateMavenRepositoryUrl = providers.gradleProperty("privateMavenRepositoryUrl").orNull
    if (privateMavenRepositoryUrl.isNullOrBlank()) {
        println("gradle property 'privateMavenRepositoryUrl' is missing. fallback to pulic maven repositories.")
    }
    repositories {
        if (!privateMavenRepositoryUrl.isNullOrBlank()) {
            maven {
                url = uri(privateMavenRepositoryUrl)
            }
        }
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    val privateMavenRepositoryUrl = providers.gradleProperty("privateMavenRepositoryUrl").orNull
    if (privateMavenRepositoryUrl.isNullOrBlank()) {
        println("gradle property 'privateMavenRepositoryUrl' is missing. fallback to pulic maven repositories.")
    }
    repositories {
        if (!privateMavenRepositoryUrl.isNullOrBlank()) {
            maven {
                url = uri(privateMavenRepositoryUrl)
            }
        }
        mavenCentral()
    }
}

gradle.startParameter.isOffline = false
if (gradle.startParameter.isOffline) {
    println("======================")
    println("gradle in offline mode")
    println("======================")
}

rootProject.name = "kotlinex"
include("app")