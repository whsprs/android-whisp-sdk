plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
}

android.namespace = "ai.whsprs.sdk.ui"

dependencies {
    api(projects.sources.common.ui)
    api(projects.sources.common.di)
    implementation(libs.lottieCompose)
}