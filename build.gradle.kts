plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.ksp) apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false

}

buildscript {
    val compose_ui_version by extra("1.1.1")
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.hilt.plugin)
    }
}