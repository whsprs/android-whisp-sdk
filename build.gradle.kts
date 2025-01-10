plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.composeCompiler) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven { setUrl("https://jitpack.io") }
    }

    dependencies {
        classpath(libs.pluginAndroidGradle)
        classpath(libs.pluginKotlinGradle)
        classpath(libs.pluginGoogleServices)
        classpath(libs.pluginNavigationArgs)
        classpath(libs.pluginProtobuf)
    }

    allprojects {
        repositories {
            google()
            mavenCentral()
            maven { setUrl("https://jitpack.io") }
            maven {
                setUrl("https://maven.pkg.github.com/Whispers-Data-Platform/wsprs-android")
                credentials {
                    username = project.findProperty("ai.whsprs.repo.user")?.toString() ?: ""
                    password = project.findProperty("ai.whsprs.repo.password")?.toString() ?: ""
                }
            }
        }
    }
}
