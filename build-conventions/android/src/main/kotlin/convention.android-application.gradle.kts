plugins {
    id("com.android.application")
    id("convention.android-common")
    id("convention.kotlin-base")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    bundle {
        language {
            /*
             * Specifies that the app bundle should not support configuration APKs for language resources.
             * These resources are instead packaged with each base and dynamic feature APK.
             */
            @Suppress("UnstableApiUsage")
            enableSplit = false
        }
    }

    defaultConfig {
        applicationId = "ai.whsprs"
    }

    testBuildType = "autoTest"

    lint {
        abortOnError = false
        warningsAsErrors = false
        textReport = true
        quiet = true
        checkReleaseBuilds = false
    }

    with(buildFeatures) {
        compose = true
    }
}
