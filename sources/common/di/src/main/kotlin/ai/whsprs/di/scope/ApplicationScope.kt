package ai.whsprs.di.scope

import javax.inject.Scope

/**
 * Main application scope. Identical to Dagger's default @Singleton scope.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope
