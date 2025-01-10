plugins {
    id("com.android.library")
    id("convention.android-common")
    id("convention.kotlin-base")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    lint {
        abortOnError = false
        warningsAsErrors = false
        textReport = true
        quiet = true
        checkReleaseBuilds = false
    }
}
