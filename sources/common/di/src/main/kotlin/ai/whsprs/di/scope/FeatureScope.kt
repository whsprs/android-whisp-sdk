package ai.whsprs.di.scope

import javax.inject.Scope

/**
 * Custom scope for features-modules. Each feature-module is considered a separate unit and
 * its component must be marked with this annotation. Internal dependencies living inside
 * feature-modules should also be marked with this annotation.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope