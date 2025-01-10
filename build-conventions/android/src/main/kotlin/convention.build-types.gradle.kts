import com.android.build.gradle.BaseExtension
import com.android.builder.internal.ClassFieldImpl
import ai.whsprs.environment.Environment
import ai.whsprs.environment.config.BuildConfigField

configure<BaseExtension> {
    signingConfigs {
        getByName("debug") {
            val env = Environment.debug
            storeFile = File("${project.rootDir}/app/signing/debug.keystore")
            storePassword = env.signing.storePassword
            keyAlias = env.signing.keyAlias
            keyPassword = env.signing.keyPassword
        }
        create("release") {
            val env = Environment.production
            storeFile = File("${project.rootDir}/app/signing/release.keystore")
            storePassword = env.signing.storePassword
            keyAlias = env.signing.keyAlias
            keyPassword = env.signing.keyPassword
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = !isAndroidLibrary
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            Environment.production.buildConfig
                .map { it.asClassField }
                .onEach(::addBuildConfigField)
        }
        getByName("debug") {
            versionNameSuffix("-dev")
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            matchingFallbacks += listOf("release")
            Environment.debug.buildConfig
                .map { it.asClassField }
                .onEach(::addBuildConfigField)
        }
        create("stage") {
            versionNameSuffix("")
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            isShrinkResources = !isAndroidLibrary
            isDebuggable = false
            matchingFallbacks += listOf("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            Environment.debug.buildConfig
                .map { it.asClassField }
                .onEach(::addBuildConfigField)
            addBuildConfigField(ClassFieldImpl("boolean", "DEBUG", "true"))
        }
        create("autoTest") {
            initWith(getByName("debug"))
            matchingFallbacks += listOf("release")
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
}

inline val BuildConfigField.asClassField: ClassFieldImpl
    get() = ClassFieldImpl(value.javaClass.simpleName, key, "\"$value\"")
inline val isAndroidLibrary: Boolean
    get() = project.pluginManager.hasPlugin("convention.android-library")
