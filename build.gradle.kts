buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.android.tools.build:gradle:3.6.4")



//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
}