package ai.whsprs.core.mvi.tea.util

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ai.whsprs.core.mvi.lifecycle.launchRepeatOnResumed
import ai.whsprs.core.mvi.lifecycle.launchRepeatOnStarted
import ai.whsprs.core.mvi.tea.Store
import ai.whsprs.core.mvi.tea.component.Renderer

/**
 * Binds view with store
 *
 * @param lifecycleOwner - lifecycle owner with lifecycle
 * @param stateRenderer - renderState function with [UiState]
 * @param effectRenderer - renderEffect function with [Effect]
 */
fun <Effect : Any, UiState : Any> Store<Effect, *, UiState>.bind(
    lifecycleOwner: LifecycleOwner,
    stateRenderer: Renderer<UiState>? = null,
    effectRenderer: Renderer<Effect>? = null,
) {
    if (stateRenderer != null) {
        lifecycleOwner.launchRepeatOnStarted {
            state.onEach(stateRenderer::render).launchIn(scope = this)
        }
    }

    if (effectRenderer != null) {
        lifecycleOwner.launchRepeatOnResumed {
            effects.onEach(effectRenderer::render).launchIn(scope = this)
        }
    }
}
