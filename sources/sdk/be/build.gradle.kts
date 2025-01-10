plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "ai.whsprs.sdk.be"

dependencies {
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinCoroutinesCore)
    implementation(libs.kotlinSerialization)
    implementation(libs.okhttp)
}