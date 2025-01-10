import com.github.burrunan.s3cache.AwsS3BuildCache

rootProject.name = "android-whisp-sdk"

plugins {
    id("com.github.burrunan.s3-build-cache") version "1.8.1"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }
    pluginManagement {
        repositories {
            google()
            mavenCentral()
            maven { setUrl("https://plugins.gradle.org/m2/") }
        }
    }
}

buildCache {
    local {
        isEnabled = false
    }
    remote<AwsS3BuildCache> {
        region = "us-east-2"
        bucket = "github-runner-gradle-cache"
        prefix = "cache/" + System.getenv("GITHUB_REPOSITORY") + "/"
        lookupDefaultAwsCredentials = true
        isPush = System.getenv().containsKey("CI")
    }
}

includeBuild("build-settings")
includeBuild("build-conventions")

// Sort all dependencies in alphabetical order
include(
    ":sample",
    ":sources:common:di",
    ":sources:common:ui",
    ":sources:core:mvi",
    ":sources:sdk:ui",
    ":sources:sdk:be",
    ":sources:sdk:all",
)


/**
 * Recursively include all project modules
 */
fun includeRecursive(root: File) {
    root.walkTopDown()
        .filter(File::isDirectory)
        .map(File::getCanonicalPath)
        .filter(::isProjectModule)
        .map(::extractModule)
        .distinct()
        .onEach { module -> include(":$module") }
        .toList()
}

fun isProjectModule(path: String): Boolean {
    return path.contains("feature-") || path.contains("domain-") || path.contains("common-")
}

fun extractModule(path: String): String {
    return path
        .substringAfter("android/")
        .substringBefore("/build")
        .substringBefore("/src")
        .replace(File.separatorChar, ':')
}
