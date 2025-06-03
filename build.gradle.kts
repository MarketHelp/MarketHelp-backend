import org.gradle.api.JavaVersion.VERSION_21
import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

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

registerOpenApiTask("graphontext", "graphontext.yaml", mode = "kotlin-server")
registerOpenApiTask("yamarket", "ya-market/ya-market.yaml", includeDtoSuffix = false)

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }

    val openapiTasks = tasks.filter { it.name.contains("openApiGenerate") && it.name != "openApiGenerate" }
        .toSet()

    dependsOn(openapiTasks)
}

tasks.withType<Tar> {
    archiveFileName = "app.tar"
}

application {
    mainClass = "ru.ovrays.graphontext.ApplicationKt"
}

detekt {
    config.setFrom("detekt.yml")
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
    implementation(libs.kora.client)

    implementation(libs.pdfcrowd)
    implementation(libs.jwt.api)
    implementation(libs.postgresql)
    implementation(libs.bcrypt)

    implementation(libs.kandy.api)
    implementation(libs.kandy.plot)
    implementation(libs.kandy.stats)

    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    ksp(libs.kora.ksp)
}

fun registerOpenApiTask(
    name: String,
    specification: String,
    mode: String = "kotlin-client",
    includeDtoSuffix: Boolean = true
) {
    val taskName = "openApiGenerate" + name.capitalized()

    tasks.register(taskName, GenerateTask::class) {
        generatorName = "kora"
        group = "openapi tools"

        inputSpec = "$projectDir/src/main/resources/openapi/$specification"
        outputDir = "$buildDir/generated/openapi"

        apiPackage = "ru.ovrays.$name.api"
        modelPackage = "ru.ovrays.$name.model"
        invokerPackage = "ru.ovrays.$name.invoker"

        if (includeDtoSuffix) {
            modelNameSuffix = "Dto"
        }

        skipValidateSpec = true

        configOptions = mapOf(
            "mode" to mode,
            "dateLibrary" to "java8",
        )
    }
}