plugins {
    alias(libs.plugins.android.application)
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.foodplanner_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodplanner_project"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Lottie
    implementation("com.airbnb.android:lottie:4.1.0") // Update to the latest version

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.5") // Consider updating to a newer version

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0") // Updated to the latest version
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.1")
    annotationProcessor("androidx.room:room-compiler:2.4.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    // Circle ImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //google
    implementation ("com.google.android.gms:play-services-auth:21.2.0")


    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")



    implementation ("de.hdodenhof:circleimageview:3.1.0")
}