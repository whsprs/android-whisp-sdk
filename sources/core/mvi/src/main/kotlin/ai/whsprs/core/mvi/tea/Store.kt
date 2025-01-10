package ai.whsprs.core.mvi.tea

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class Store<Effect : Any, UiEvent : Any, UiState : Any> : ViewModel() {

    abstract val effects: Flow<Effect>

    abstract val state: StateFlow<UiState>

    abstract fun accept(event: UiEvent)
}