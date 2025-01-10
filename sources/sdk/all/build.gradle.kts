plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "ai.whsprs.sdk.all"

dependencies {
    api(projects.sources.sdk.ui)
    api(projects.sources.sdk.be)
    implementation(libs.timber)
    implementation(libs.kotlinSerialization)
    implementation(libs.bundles.retrofit)
    implementation(libs.okhttp)
}