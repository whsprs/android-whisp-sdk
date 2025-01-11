plugins {
    alias(libs.plugins.composeCompiler)
    `maven-publish`
    id("convention.android-library-compose")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "ai.whsprs.sdk.all"
group = "ai.whsprs.android.sdk"
version = "0.0.1"


publishing.publications.withType<MavenPublication> {
    pom {
        name.set("Whisp Android SDK")
        description.set("Whisp AI Agents orchestration protocol SDK for Android")
        url.set("https://github.com/whsprs/android-whisp-sdk")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/whsprs/android-whisp-sdk/blob/develop/LICENSE")
            }
        }
        developers {
            developer {
                id.set("0xwhspr")
                name.set("Whisp Runner")
                url.set("https://github.com/0xwhspr")
            }
        }
    }
}

dependencies {
    api(projects.sources.sdk.ui)
    api(projects.sources.sdk.be)
    implementation(libs.timber)
    implementation(libs.kotlinSerialization)
    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp)
}