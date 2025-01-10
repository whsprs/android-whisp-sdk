package ai.whsprs.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * Service class for creating and storing Dagger's feature component.
 *
 * Get an instance by featureViewModel() delegate or use parentComponentViewModel() in case if you have
 * shared feature component and you need to get an instance of the existing component for a child.
 *
 * @see featureViewModel
 * @see parentComponentViewModel
 */
abstract class ComponentViewModel<T> : ViewModel() {

    private var cachedComponent: T? = null

    protected abstract fun buildComponent(fragment: Fragment): T

    fun provideComponent(fragment: Fragment): T {
        return cachedComponent ?: buildComponent(fragment).also { cachedComponent = it }
    }
}