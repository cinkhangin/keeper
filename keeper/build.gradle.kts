plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.hilt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.vanniktech.maven)
}

android {
    namespace = "com.ckgin.keeper"
    compileSdk = 36

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
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlinx.serialization.json)

    //datastore
    api(libs.androidx.datastore.preferences)
    //api(libs.androidx.datastore)
    //hilt
    implementation(libs.google.hilt.android)
    ksp(libs.google.hilt.compiler)
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    signAllPublications()

    coordinates(
        "com.ckgin",
        "keeper",
        "1.0.0-alpha01"
    )

    pom {
        name.set("Keeper")
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
                id.set("ckgin")
                name.set("Cin Khan Gin")
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