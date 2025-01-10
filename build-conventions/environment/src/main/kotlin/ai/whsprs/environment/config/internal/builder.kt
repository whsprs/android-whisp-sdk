package ai.whsprs.environment.config.internal

import ai.whsprs.environment.config.BuildConfigField

internal inline fun buildConfig(builder: BuildConfigBuilder.() -> Unit): List<Field> {
    return BuildConfigBuilder().apply(builder).build()
}

internal class BuildConfigBuilder {

    private val fields = mutableListOf<Field>()

    fun field(key: String, builder: FieldBuilder.() -> Unit): Field {
        return FieldBuilder(key).apply(builder).build()
    }

    fun config(key: String, builder: FieldBuilder.() -> Unit): Field {
        return FieldBuilder("${key}_config").apply(builder).build()
    }

    operator fun Field.unaryPlus() {
        fields += this
    }

    fun build() = fields
}

internal data class Field(val key: String, val valueByBuildType: Map<String, Any>) {

    val debug: BuildConfigField
        get() = BuildConfigField(
            key = key.removeSuffix("_config"),
            value = valueByBuildType["debug"] ?: emptyMap<String, Any>(),
            isConfig = key.contains("_config")
        )

    val release: BuildConfigField
        get() = BuildConfigField(
            key = key.removeSuffix("_config"),
            value = valueByBuildType["release"] ?: emptyMap<String, Any>(),
            isConfig = key.contains("_config")
        )
}

internal class FieldBuilder(key: String) {

    private var field: Field = Field(key, emptyMap())

    fun debug(builder: FieldByBuildTypeBuilder.() -> Unit) {
        val valueByBuildType = FieldByBuildTypeBuilder("debug").apply(builder).build()
        field = field.copy(valueByBuildType = field.valueByBuildType + valueByBuildType)
    }

    fun release(builder: FieldByBuildTypeBuilder.() -> Unit) {
        val valueByBuildType = FieldByBuildTypeBuilder("release").apply(builder).build()
        field = field.copy(valueByBuildType = field.valueByBuildType + valueByBuildType)
    }

    fun value(builder: FieldByBuildTypeBuilder.() -> Unit) {
        debug(builder)
        release(builder)
    }

    fun build() = field
}

internal class FieldByBuildTypeBuilder(private val buildType: String) {

    private val valueByBuildType = mutableMapOf<String, Any>()

    operator fun Any.unaryPlus() {
        valueByBuildType[buildType] = this
    }

    fun build(): Map<String, Any> = valueByBuildType
}
