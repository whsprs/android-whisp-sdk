import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLanguageVersion = "1.9"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()

        allWarningsAsErrors = false

        languageVersion = kotlinLanguageVersion
        apiVersion = kotlinLanguageVersion

        freeCompilerArgs = buildList {
            addAll(freeCompilerArgs)
            add("-opt-in=io.kotest.common.ExperimentalKotest")
            add("-opt-in=kotlin.RequiresOptIn")
            add("-opt-in=kotlin.time.ExperimentalTime")
            add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
            add("-opt-in=kotlinx.coroutines.FlowPreview")
            add("-progressive")
            add("-Xcontext-receivers")

            // Inheritance from an interface with '@JvmDefault'
            // members is only allowed with -Xjvm-default option
            add("-Xjvm-default=all")

            // Uncomment to get Compose compiler reports in app/build/compose_compiler, use for release build to get accurate results
            // more details: https://developer.android.com/develop/ui/compose/performance/stability/diagnose#kotlin
//            add("-P")
//            add("plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler")

            // To prevent optimised out variables in debug mode
            val isCI = System.getenv().getOrDefault("CI", "false").toBoolean()
            if (!isCI) add("-Xdebug")
        }
    }
}
