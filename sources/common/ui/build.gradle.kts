plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
}

android.namespace = "ai.whsprs.common.ui_compose"

dependencies {
    api(libs.androidxActivityCompose)
    api(libs.androidxComposeAnimation)
    api(libs.androidxComposeMaterial)
    api(libs.androidxComposeMaterial3)
    api(libs.androidxComposeRuntime)
    api(libs.androidxComposeUi)
    api(libs.androidxComposeUiTooling)
    api(libs.androidxComposeUiToolingPreview)
    api(libs.composeShimmer)
    api(libs.androidxComposeFoundation)
    api(platform(libs.androidxComposeBom))
    api(libs.markdown)
    implementation(libs.androidxFragmentKtx)
    implementation(libs.coilCompose)
    implementation(libs.lottieCompose)
//    implementation(projects.sources.common.util)
}