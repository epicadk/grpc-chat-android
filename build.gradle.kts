// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath(Plugins.ClassPath.androidGradlePlugin)
    classpath(Plugins.ClassPath.koltinGradlePlugin)
    classpath(Plugins.ClassPath.protobufGradlePlugin)
    classpath(Plugins.ClassPath.hiltPlugin)
    classpath(Plugins.ClassPath.nav_args)

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
  configureSpotless()
}
