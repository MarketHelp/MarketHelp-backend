import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp.devtools)
    alias(libs.plugins.openapi.generator)
    alias(libs.plugins.detekt)

    application
}

group = "ru.ovrays"
version = "1.0.0"

java {
    sourceCompatibility = VERSION_21
}

kotlin {
    jvmToolchain(21)

    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
        kotlin.srcDir("build/generated/openapi")
    }

    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }

    dependsOn(tasks.openApiGenerate)
}

application {
    mainClass = "ru.ovrays.graphontext.ApplicationKt"
}

buildscript {
    dependencies {
        classpath(libs.kora.openapi.generator)
    }
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.coroutines.reactor)

    implementation(platform(libs.kora.bom))

    implementation(libs.kora.http.server)
    implementation(libs.kora.config)
    implementation(libs.kora.json)
    implementation(libs.kora.jdbc)
    implementation(libs.kora.jackson)
    implementation(libs.kora.liquibase)
    implementation(libs.kora.logback)

    implementation(libs.jwt.api)
    implementation(libs.postgresql)
    implementation(libs.bcrypt)

    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    ksp(libs.kora.ksp)
}

openApiGenerate {
    generatorName = "kora"
    group = "openapi tools"

    inputSpec = "$projectDir/src/main/resources/openapi/graphontext.yaml"
    outputDir = "$buildDir/generated/openapi"

    apiPackage = "ru.ovrays.graphontext.api"
    modelPackage = "ru.ovrays.graphontext.model"
    invokerPackage = "ru.ovrays.graphontext.invoker"

    modelNameSuffix = "Dto"
    skipValidateSpec = true

    configOptions = mapOf(
        "mode" to "kotlin-server",
        "dateLibrary" to "java8",
    )
}