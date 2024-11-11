import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.protobuf)
    id("kotlin-parcelize")

}

android {
    namespace = "com.kakaotech.team25"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kakaotech.team25"
        minSdk = 27
        targetSdk = 34
        versionCode = 4
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_API_KEY", getApiKey("KAKAO_API_KEY"))
        buildConfigField("String", "KAKAO_REST_API_KEY", getApiKey("KAKAO_REST_API_KEY"))
        buildConfigField("String", "KAKAO_BASE_URL", getApiUrl("KAKAO_BASE_URL"))
        buildConfigField("String", "S3_ACCESS_KEY", getApiKey("S3_ACCESS_KEY"))
        buildConfigField("String", "S3_SECRET_KEY", getApiKey("S3_SECRET_KEY"))
        buildConfigField("String", "API_BASE_URL", getApiUrl("API_BASE_URL"))
        buildConfigField("String", "CARD_SECRET_KEY", getApiKey("CARD_SECRET_KEY"))
        manifestPlaceholders["kakaoApiKey"] = getApiKey("KAKAO_API_KEY_NO_QUOTES")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.datastore)
    implementation(libs.protobuf.javalite)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.kakao.sdk.all)
    implementation(libs.kakao.map)
    implementation(libs.v2.user)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.datastore.core.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    ksp(libs.androidx.room.compiler)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.aws.android.sdk.s3)
    implementation(libs.aws.android.sdk.mobile.client)
    implementation(libs.aws.android.sdk.core)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}

fun getApiKey(key: String): String = gradleLocalProperties(rootDir, providers).getProperty(key, "")
fun getApiUrl(key: String): String = gradleLocalProperties(rootDir, providers).getProperty(key, "")
