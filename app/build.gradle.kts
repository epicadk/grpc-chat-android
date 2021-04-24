import com.google.protobuf.gradle.*

 plugins {
            id (Plugins.BuildPlugins.androidApp)
            id (Plugins.BuildPlugins.kotlinAndroid)
            id (Plugins.BuildPlugins.protobuf)
            id (Plugins.BuildPlugins.kapt)
            id ("dagger.hilt.android.plugin")
            id ( "androidx.navigation.safeargs.kotlin")
 }

android {
    compileSdkVersion(SDK.compileSdk)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.example.grpc_chat_android"
        minSdkVersion(SDK.mindSdk)
        targetSdkVersion(SDK.targetSdk)
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = Dependencies.Test.junitTestRunner
    }

    buildFeatures{
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility (JavaVersion.VERSION_1_8)
        targetCompatibility (JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

protobuf{

    protoc{
        artifact = Plugins.Protobuf.protoc
    }

    plugins {
        //id("javalite") { artifact = Plugins.Protobuf.javaliteProtogen}
        id("grpc") {
            artifact = Plugins.Protobuf.grpcJava
        }
    }

    generateProtoTasks {
        all().forEach{ task ->
            task.builtins{
                java {}
            }
            task.plugins {
                id("java"){
                    option("lite")
                }
                id("grpc"){
                    option("lite")
                }
            }
        }
    }
}

dependencies {

    implementation(Dependencies.Kotlin.stdlib)
    implementation (Dependencies.AndroidX.appCompat)
    implementation (Dependencies.material)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    testImplementation (Dependencies.Test.junit)
    implementation (Dependencies.LifeCycle.livedata)
    implementation (Dependencies.LifeCycle.viewModel)
    implementation (Dependencies.AndroidX.fragmentKtx)
    androidTestImplementation (Dependencies.Test.extJunit)
    //androidTestImplementation (Dependencies.Test.espresso_core)
    implementation (Dependencies.Grpc.okHttp)
    implementation (Dependencies.Grpc.protobuf)
    implementation (Dependencies.Grpc.stub)
    implementation (Dependencies.tomcatAnnotations)
    implementation (Dependencies.multidex)
    implementation (Dependencies.Room.roomRuntime)
    kapt (Dependencies.Room.roomCompiler)
    kapt (Dependencies.Room.roomKtx)
    implementation ("com.google.dagger:hilt-android:2.34.1-beta")
    kapt ("com.google.dagger:hilt-compiler:2.34.1-beta")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.34.1-beta")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.34.1-beta")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.34.1-beta")
    kaptTest ("com.google.dagger:hilt-compiler:2.34.1-beta")
    implementation ("androidx.datastore:datastore-preferences:1.0.0-beta01")
    val nav_version = "2.3.5"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")
}