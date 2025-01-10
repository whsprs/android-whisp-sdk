plugins {
    id("convention.android-application")
    id("convention.dagger-compiler")
    kotlin("plugin.serialization") version libs.versions.kotlin
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ai.whsprs"
}

dependencies {
    implementation(projects.sources.sdk.all)
    implementation(libs.androidxFragmentKtx)
}