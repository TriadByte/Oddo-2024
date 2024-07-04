// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2") // Use the latest version
        classpath("com.google.gms:google-services:4.3.15") // Use the latest version
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
