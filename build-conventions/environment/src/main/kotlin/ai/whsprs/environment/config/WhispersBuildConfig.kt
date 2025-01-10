package ai.whsprs.environment.config

import ai.whsprs.environment.config.internal.Field
import ai.whsprs.environment.config.internal.buildConfig

/**
 * Represents application BuildConfig.
 * Each field will be automatically applied to `debug` and `release` build types.
 */
internal val whispersBuildConfig = buildConfig {

    +field("LANGUAGES") {
        value { +"en,de,es,fr,it,pt" }
    }
}

internal inline val debugBuildConfig: List<BuildConfigField>
    get() = whispersBuildConfig.map(Field::debug)

internal inline val releaseBuildConfig: List<BuildConfigField>
    get() = whispersBuildConfig.map(Field::release)
