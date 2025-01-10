import ai.whsprs.util.implementation
import ai.whsprs.util.ksp
import ai.whsprs.util.withVersionCatalog
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

project.withVersionCatalog { libs ->
    plugins.apply(libs.plugins.ksp.get().pluginId)

    plugins.withType<KotlinBasePluginWrapper> {
        dependencies {
            implementation(libs.daggerCore)
            implementation(project(":sources:common:di"))
            ksp(libs.daggerCompiler)
        }
    }
}