plugins {
    id("convention.android-library")
}

android.namespace = "ai.whsprs.core.mvi"

dependencies {
    implementation(libs.bundles.androidxLifecycle)
    implementation(libs.timber)
}