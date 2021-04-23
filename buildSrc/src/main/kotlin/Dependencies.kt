
object Dependencies {

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.app_compat}"

        //const val contraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    }

    object Room {


       const val roomRuntime = "androidx.room:room-runtime:${Versions.room_version}"
       const val roomCompiler ="androidx.room:room-compiler:${Versions.room_version}"
       const val  roomKtx =  "androidx.room:room-ktx:${Versions.room_version}"

    }

    object Grpc {
        const val okHttp = "io.grpc:grpc-okhttp:${Versions.grpc}"
        const val protobuf = "io.grpc:grpc-protobuf-lite:${Versions.grpc}"
        const val stub = "io.grpc:grpc-stub:${Versions.grpc}"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object LifeCycle {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    }

    object Test {
        const val junit = "junit:junit:4.+"
        const val extJunit = "androidx.test.ext:junit:${Versions.ext_junit}"
        const val espresso_core =
            "androidx.test.espresso_core:${Versions.espresso_core}"
        const val junitTestRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    const val material = "com.google.android.material:material:${Versions.material}"
    const val tomcatAnnotations = "org.apache.tomcat:annotations-api:${Versions.tomcat}"
    const val multidex = "com.android.support:multidex:${Versions.multidex}"

    object Versions {
        const val grpc = "1.37.0"
        const val lifecycle = "2.3.1"
        const val fragment = "1.3.2"
        const val kotlin = "1.4.32"
        const val espresso_core = "3.3.0"
        const val multidex = "1.0.3"
        const val ext_junit = "1.1.2"
        const val tomcat = "6.0.53"
        const val material = "1.3.0"
        const val app_compat = "1.2.0"
        const val room_version = "2.3.0"
    }
}
