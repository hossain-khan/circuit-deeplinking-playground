import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.ksp)
  alias(libs.plugins.metro)
}

android {
  namespace = "app.example"
  compileSdk = 35

  defaultConfig {
    applicationId = "app.example"
    minSdk = 30
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    // Read API key from local.properties
    val apiKey: String =
      project.rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use {
        Properties().apply { load(it) }.getProperty("SERVICE_API_KEY")
      } ?: "YOUR_SERVICE_API_KEY"
    buildConfigField("String", "SERVICE_API_KEY", "\"$apiKey\"")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }
}

dependencies {
  // App dependencies
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.text.google.fonts)
  implementation(libs.androidx.ui.tooling.preview)

  implementation(libs.circuit.codegen.annotations)
  implementation(libs.circuit.foundation)
  implementation(libs.circuit.overlay)
  implementation(libs.circuitx.android)
  implementation(libs.circuitx.effects)
  implementation(libs.circuitx.gestureNav)
  implementation(libs.circuitx.overlays)

  // Metro dependencies
//  implementation(libs.metro.runtime)
//  implementation(libs.metro.viewmodel)
//  ksp(libs.metro.ksp)

  // Circuit with Metro
  ksp(libs.circuit.codegen)

  // Testing
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}

ksp {
  // Circuit codegen with Metro mode
  arg("circuit.codegen.mode", "METRO")
}
