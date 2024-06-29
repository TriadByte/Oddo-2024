plugins {
    alias(libs.plugins.android.application)
//    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.crimesnap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.crimesnap"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {



    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//
//    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
//    implementation("com.google.firebase:firebase-analytics")

//    implementation platform("com.google.firebase:firebase-bom:33.1.1")
    implementation ("com.google.firebase:firebase-storage")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-core")

    implementation("androidx.credentials:credentials:<latest version>")
    implementation("androidx.credentials:credentials-play-services-auth:<latest version>")
    implementation("com.google.android.libraries.identity.googleid:googleid:<latest version>")


    implementation ("org.json:json:20210307")


    val camerax_version = "1.3.3"

    implementation ("io.socket:socket.io-client:2.0.0")
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation ("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation ("androidx.camera:camera-camera2:${camerax_version}")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")

//    implementation ("com.google.firebase:firebase-firestore:24.1.0")
//    implementation ("com.google.firebase:firebase-storage:23.0.0")

    implementation ("com.squareup.picasso:picasso:2.71828")
}