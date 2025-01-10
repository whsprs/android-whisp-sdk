import com.android.build.gradle.BaseExtension
import ai.whsprs.environment.Environment
import ai.whsprs.util.withVersionCatalog

plugins {
    id("convention.build-types")
}

configure<BaseExtension> {
    project.withVersionCatalog { libs ->
        buildToolsVersion(libs.versions.buildTools.get())
        compileSdkVersion(libs.versions.compileSdk.get().toInt())

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            targetSdk = libs.versions.targetSdk.get().toInt()
            versionName = Environment.default.versionName
            versionCode = Environment.default.versionCode
            vectorDrawables {
                useSupportLibrary = true
            }

            javaCompileOptions {
                annotationProcessorOptions {
                    arguments(mutableMapOf("room.incremental" to "true"))
                }
            }
        }

        dependencies {
            add("coreLibraryDesugaring", libs.desugaring)
        }
    }

    sourceSets {
        named("main").configure { java.srcDir("src/main/kotlin") }
        named("androidTest").configure { java.srcDir("src/androidTest/kotlin") }
        named("test").configure { java.srcDir("src/test/kotlin") }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    packagingOptions {
        setExcludes(
            setOf(
                "about.html",
                "LICENSE.txt",
                "META-INF/AL2.0",
                "META-INF/com.android.tools/proguard/coroutines.pro",
                "META-INF/DEPENDENCIES",
                "META-INF/LGPL2.1",
                "META-INF/LICENSE",
                "META-INF/LICENSE-notice.md",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE.txt",
                "META-INF/licenses/ASM",
                "META-INF/MANIFEST.MF",
                "META-INF/maven/com.google.protobuf/protobuf-javalite/pom.properties",
                "META-INF/maven/com.google.protobuf/protobuf-javalite/pom.xml",
                "META-INF/maven/net.bytebuddy/byte-buddy-agent/pom.properties",
                "META-INF/maven/net.bytebuddy/byte-buddy-agent/pom.xml",
                "META-INF/maven/net.bytebuddy/byte-buddy/pom.properties",
                "META-INF/maven/net.bytebuddy/byte-buddy/pom.xml",
                "META-INF/maven/org.apache.commons/commons-lang3/pom.properties",
                "META-INF/maven/org.apache.commons/commons-lang3/pom.xml",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/proguard/coroutines.pro",
                "NOTICE.txt",
                "win32-x86-64/attach_hotspot_windows.dll",
                "win32-x86/attach_hotspot_windows.dll",
            )
        )
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"

        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    with(buildFeatures) {
        buildConfig = true
        viewBinding = true
    }
}
