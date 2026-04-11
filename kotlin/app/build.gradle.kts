import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jvmVersion = providers.gradleProperty("jvmVersion").get().toInt()
val kotlinVersion = providers.gradleProperty("kotlinVersion").get()
val kotlinLoggingVersion = providers.gradleProperty("kotlinLoggingVersion").get()

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    // This dependency is used by the application.
    implementation(libs.guava)
    implementation("org.slf4j:slf4j-api:2.0.13")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.6")
    // Source: https://mvnrepository.com/artifact/com.fasterxml.jackson/jackson-bom
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.21.1"))
}

// set java source compatibility for java compile task
java {
    // minimum java version to compile source code
    sourceCompatibility = JavaVersion.toVersion(jvmVersion)
    // minimum java version to run the compiled bytecode, same or bigger than sourceCompatibility
    targetCompatibility = JavaVersion.toVersion(jvmVersion)

    // foojay-resolver: Apply a specific Java toolchain to ease working on different environments.
    // foojay downloads specified JDK version if not found in .gradle/toolchains/ so gradlew can automatically setup the JDK toolchain.
    toolchain {
        languageVersion = JavaLanguageVersion.of(jvmVersion)
    }
}

// set jvm toolchain for kotlin compile task
kotlin {
    jvmToolchain(jvmVersion)
}

// Configure the kotlin built-in test suite to use the JUnit Jupiter test framework.
testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest(kotlinVersion)
        }
    }
}

// kotlin compiler options
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.fromTarget(jvmVersion.toString()))
    }
}

// Create a executable fat JAR that includes all dependencies
tasks.register<Jar>("fatJar") {
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "org.example.AppKt"
    }
    from(sourceSets.main.get().output)
    from(configurations.runtimeClasspath.get().filter { it.exists() }.map { if (it.isDirectory) it else zipTree(it) })
}

// Make the 'assemble' task depend on the 'fatJar' task
tasks.named("assemble") {
    dependsOn("fatJar")
}

/*
 * # clean a specific test result cache before run test
 * gradle test --rerun --tests 'Hello*.hello'
 * # clean all tests result cache before run tests
 * gradle test --rerun-tasks --tests 'Hello*.hello'
 * gradle test --rerun-tasks --tests 'Hello*.hello' --info
 */
tasks.test {
    testLogging {
        showStandardStreams = true
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
}

/*
    build executable fat JAR: ./gradlew :app:fatJar
    build artifacts: app/build/libs/app-all.jar
    execution method: java -jar app/build/libs/app-all.jar
*/