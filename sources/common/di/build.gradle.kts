plugins {
    id("convention.android-library")
}

android.namespace = "ai.whsprs.di"

dependencies {
    api(libs.daggerCore)
    implementation(libs.androidxActivityKtx)
    implementation(libs.androidxAppcompat)
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxFragmentKtx)
    implementation(libs.gson)
}