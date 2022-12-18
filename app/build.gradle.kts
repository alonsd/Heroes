plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}


android {
    compileSdk = 33
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }


    defaultConfig {
        applicationId = "com.heroes"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    //Used for compose navigation code generator
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}

dependencies {
    //AppCompat
    implementation("androidx.appcompat:appcompat:1.5.1")

    //Compose
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.material:material:1.4.0-alpha03")
    implementation("androidx.compose.animation:animation:1.4.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling:1.4.0-alpha03")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.0-alpha03")

    //Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    testImplementation("com.google.truth:truth:1.1.3")

    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-android-extensions-runtime:1.7.22")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    //Ok Http Logging
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")

    //NetworkResponseAdapter
    implementation("com.github.haroldadmin:NetworkResponseAdapter:4.2.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //Material
    implementation("com.google.android.material:material:1.8.0-beta01")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    //Gson
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-compiler:2.44.2")

    //Compose Destinations
    implementation("io.github.raamcosta.compose-destinations:animations-core:1.7.25-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.7.25-beta")

    //Compose Shimmering
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.3")

}

kapt {
    generateStubs = true
}
