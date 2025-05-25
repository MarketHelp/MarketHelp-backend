rootProject.name = "graphontext"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/kds/kotlin-ds-maven")
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

buildscript {
    repositories {
        mavenCentral()
        maven("https://packages.jetbrains.team/maven/p/kds/kotlin-ds-maven")
    }
}