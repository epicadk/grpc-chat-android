object Plugins {
    object ClassPath {
        const val androidGradlePlugin =  "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
        const val koltinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Versions.kotlin}"
        const val protobufGradlePlugin =  "com.google.protobuf:protobuf-gradle-plugin:${Versions.protobufGradlePlugin}"
    }

    object BuildPlugins {
        const val protobuf = "com.google.protobuf"
        const val kotlinAndroid = "kotlin-android"
        const val androidApp = "com.android.application"
    }
    object Protobuf {
        const val javaliteProtogen = "com.google.protobuf:protoc-gen-javalite:${Versions.javaLiteProtogen}"
        const val grpcJava = "io.grpc:protoc-gen-grpc-java:${Versions.grpcJava}"
        const val protoc = "com.google.protobuf:protoc:${Versions.protoc}"
    }
    object Versions{
        const val androidGradlePlugin = "4.1.3"
        const val protobufGradlePlugin = "0.8.15"
        const val javaLiteProtogen = "3.0.0"
        const val grpcJava = "1.37.0"
        const val protoc = "3.12.0"
    }
}