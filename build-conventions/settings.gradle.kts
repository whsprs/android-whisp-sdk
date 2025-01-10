rootProject.name = "build-conventions"

pluginManagement {
    includeBuild("../build-settings")
}

plugins {
    id("convention-dependencies")
}

include("android")
include("kotlin")
include("environment")
include("util")