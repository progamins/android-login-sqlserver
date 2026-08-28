plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.login"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.login"
        minSdk = 32
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Server config via local.properties — never hardcode IPs/creds in repo.
        // Define in local.properties: DB_IP, DB_USER, DB_PASSWORD, DB_NAME
        // Fallbacks below are placeholders for build without local.properties
        val dbIp: String = (project.findProperty("DB_IP") as String?) ?: "YOUR_SERVER_IP:PORT"
        val dbUser: String = (project.findProperty("DB_USER") as String?) ?: "YOUR_DB_USER"
        val dbPassword: String = (project.findProperty("DB_PASSWORD") as String?) ?: "YOUR_DB_PASSWORD"
        val dbName: String = (project.findProperty("DB_NAME") as String?) ?: "YOUR_DB_NAME"
        buildConfigField("String", "DB_IP", "\"$dbIp\"")
        buildConfigField("String", "DB_USER", "\"$dbUser\"")
        buildConfigField("String", "DB_PASSWORD", "\"$dbPassword\"")
        buildConfigField("String", "DB_NAME", "\"$dbName\"")
    }
    buildFeatures {
        buildConfig = true
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
    implementation(files("libs\\jtds-1.3.1.jar"))
    implementation(files("libs\\mssql-jdbc-12.6.3.jre11.jar"))
    implementation(files("libs\\mssql-jdbc-12.6.3.jre11.jar"))
    implementation(files("libs\\mssql-jdbc-12.6.3.jre11.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}