package ai.whsprs.di

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<T : ViewModel>(
    private val create: () -> T
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create.invoke() as T
    }
}

/**
 * Delegate for creating or retrieving ViewModel from ViewModelStore.
 * Use for ComponentViewModel and feature ViewModel.
 *
 * @see androidx.lifecycle.ViewModel
 * @see androidx.lifecycle.ViewModelStore
 * @see ComponentViewModel
 */
inline fun <reified T : ViewModel> Fragment.featureViewModel(
    noinline create: () -> T
) = viewModels<T> { ViewModelFactory(create) }

inline fun <reified T : ViewModel> AppCompatActivity.activityViewModel(
    noinline create: () -> T
) = viewModels<T> { ViewModelFactory(create) }