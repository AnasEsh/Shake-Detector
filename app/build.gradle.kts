plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.anas.shake_detector"
    compileSdk = 31

    defaultConfig {
        applicationId = "com.anas.shake_detector"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }

}

dependencies {

    // Compose view models
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.0-beta01")

    // Core KTX
    implementation("androidx.core:core-ktx:1.7.0")
    
    // Material Design Components (required for themes)
    implementation("com.google.android.material:material:1.6.0")
    
    // AppCompat (required for compatibility)
    implementation("androidx.appcompat:appcompat:1.4.2")

    // Kotlin BOM
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.6.20"))

    // Lifecycle runtime
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1") // Keep as is, no matching version in the first list

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.3.1")

    // Compose BOM
//    implementation platform(")androidx.compose:compose-bom:2022.10.00"))

    // Compose UI
    implementation("androidx.compose.ui:ui:1.2.0-alpha08")
    implementation("androidx.compose.ui:ui-graphics:1.2.0-alpha08")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-alpha08")
    implementation ("androidx.compose.material:material:1.2.0-alpha08")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    implementation(project(":ShakeDetector"))
    // Test dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}