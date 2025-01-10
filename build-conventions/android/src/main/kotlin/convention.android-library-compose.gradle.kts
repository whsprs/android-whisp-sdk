import com.android.build.gradle.BaseExtension

plugins {
    id("convention.android-library")
}

configure<BaseExtension> {
    with(buildFeatures) {
        compose = true
    }
}