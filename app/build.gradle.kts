import com.google.protobuf.gradle.*

 plugins {
            id (Plugins.BuildPlugins.androidApp)
            id (Plugins.BuildPlugins.kotlinAndroid)
            id (Plugins.BuildPlugins.protobuf)
            id (Plugins.BuildPlugins.kapt)
            id (Plugins.BuildPlugins.hilt)
            id ( Plugins.BuildPlugins.safeArgs)
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
    annotationProcessor(Dependencies.Room.roomCompiler)
    kapt(Dependencies.Room.roomKtx)
    implementation (Dependencies.Hilt.hilt)
    kapt (Dependencies.Hilt.compiler)
    androidTestImplementation  (Dependencies.Hilt.testing)
    kaptAndroidTest (Dependencies.Hilt.compiler)
    testImplementation (Dependencies.Hilt.testing)
    kaptTest (Dependencies.Hilt.compiler)
    implementation (Dependencies.preferenceDatastore)
    implementation (Dependencies.Navigation.navigationFragment)
    implementation (Dependencies.Navigation.navigationUI)
    testImplementation(Dependencies.Test.roboElectric)
}