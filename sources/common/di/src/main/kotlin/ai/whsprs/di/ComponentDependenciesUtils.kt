package ai.whsprs.di

import android.app.Activity
import androidx.fragment.app.Fragment

inline fun <reified T : ComponentDependencies> Activity.provideComponentDependencies(): T {
    return provideComponentDependencies(T::class.java) as T
}

inline fun <reified T : ComponentDependencies> Fragment.provideComponentDependencies(): T {
    return provideComponentDependencies(T::class.java) as T
}

fun <T : ComponentDependencies> Fragment.provideComponentDependencies(cls: Class<T>): ComponentDependencies {
    var deps: ComponentDependencies? = null
    if (this is ComponentDependenciesHolder) deps = dependencies[cls]
    if (deps == null && parentFragment != null) deps = requireParentFragment().provideComponentDependencies(cls)
    if (deps == null) deps = requireActivity().provideComponentDependencies(cls)
    return deps
}

fun <T : ComponentDependencies> Activity.provideComponentDependencies(cls: Class<T>): ComponentDependencies {
    var deps: ComponentDependencies? = null
    if (this is ComponentDependenciesHolder) deps = dependencies[cls]
    return deps ?: throw RuntimeException("Couldn't find ${cls.name} dependency")
}