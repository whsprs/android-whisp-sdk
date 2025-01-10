plugins {
    `kotlin-dsl`
}

group = "ai.whsprs.build-conventions"

dependencies {
    implementation(libs.pluginKotlinGradle)
    implementation(projects.environment)
}
