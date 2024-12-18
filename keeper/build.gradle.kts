import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)
    alias(libs.plugins.kotlinx.serialization)
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "com.naulian.keeper"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlinx.serialization.json)

    //datastore
    api(libs.androidx.datastore.preferences)
    //hilt
    implementation(libs.google.hilt.android)
    ksp(libs.google.hilt.compiler)
}

val mavenCentralUsername: String? = System.getenv("MAVEN_CENTRAL_USERNAME")
val mavenCentralPassword: String? = System.getenv("MAVEN_CENTRAL_PASSWORD")
val signingKeyId: String? = System.getenv("SIGNING_KEY_ID")
val signingPassword: String? = System.getenv("SIGNING_PASSWORD")
val signingSecretKeyRingFile: String? = System.getenv("SIGNING_SECRET_KEY_RING_FILE")



mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

    signAllPublications()

    coordinates("com.naulian", "keeper", "0.1.0-alpha01")

    pom {
        name.set("Modify")
        description.set("An Android library to easily setup DataStore for simple apps")
        inceptionYear.set("2024")
        url.set("https://github.com/cinkhangin/keeper/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("naulian")
                name.set("Naulian")
                url.set("https://github.com/cinkhangin/")
            }
        }
        scm {
            url.set("https://github.com/cinkhangin/keeper/")
            connection.set("scm:git:git://github.com/cinkhangin/keeper.git")
            developerConnection.set("scm:git:ssh://git@github.com/cinkhangin/keeper.git")
        }
    }
}