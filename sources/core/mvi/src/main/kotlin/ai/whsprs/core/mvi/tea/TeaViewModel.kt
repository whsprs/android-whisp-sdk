package ai.whsprs.core.mvi.tea

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ai.whsprs.core.mvi.tea.component.Actor
import ai.whsprs.core.mvi.tea.component.Reducer
import ai.whsprs.core.mvi.tea.component.UiStateMapper
import ai.whsprs.core.mvi.tea.util.combineActors
import timber.log.Timber

/**
 * [Store], TEA (The Elm Architecture) core, MVI like architecture
 *
 * Store accepts initial [State], set of [Actor]'s, [Reducer] and [UiStateMapper]
 *
 * It is also necessary to pass all parts of tea as generics. Exactly:
 * - [Command] - that triggers [Actor], see [Actor] for more details
 * - [Effect] - single live event
 * - [Event] - Event that triggers [Reducer], see [Reducer] for more details
 * - [UiEvent] - Ui event. Extends from [Event]. Sent from the UI
 * - [State] - Presentation layer state
 * - [UiState] - UI layer state
 *
 * Provide it like [ViewModel] with view model factory, because it's extends from [ViewModel]
 */
abstract class TeaViewModel<Command : Any, Effect : Any, Event : Any, UiEvent : Event, State : Any, UiState : Any>(
    initialState: State,
    actor: Actor<Command, Event>,
    reducer: Reducer<Command, Effect, Event, State>,
    initialEvents: List<Event> = emptyList(),
    private val uiStateMapper: UiStateMapper<State, UiState>? = null
) : Store<Effect, UiEvent, UiState>() {

    override val effects: Flow<Effect>
        get() = effectsChannel.receiveAsFlow()

    override val state: StateFlow<UiState>
        get() = uiStateFlow.asStateFlow()

    private val stateFlow = MutableStateFlow(initialState)

    private val uiStateFlow = MutableStateFlow(initialState.toUiState())
    private val commandsFlow = MutableSharedFlow<Command>(replay = 1)
    private val effectsChannel = Channel<Effect>(Channel.BUFFERED)
    private val eventsFlow = MutableSharedFlow<Event>(replay = 1)

    constructor(
        initialState: State,
        actors: Set<Actor<Command, Event>>,
        reducer: Reducer<Command, Effect, Event, State>,
        initialEvents: List<Event> = emptyList(),
        uiStateMapper: UiStateMapper<State, UiState>? = null
    ) : this(initialState, combineActors(actors), reducer, initialEvents, uiStateMapper)

    init {
        setupStateFlow()
        setupCommandsFlow(actor)
        setupEventsFlow(reducer)

        applyInitialEvents(initialEvents)
    }

    @CallSuper
    override fun onCleared() {
        Timber.tag("Lifecycle").i("${this.javaClass.simpleName} – onCleared")
        super.onCleared()
    }

    override fun accept(event: UiEvent) {
        viewModelScope.launch { eventsFlow.emit(event) }
    }

    private fun setupStateFlow() {
        stateFlow
            .map { it.toUiState() }
            .distinctUntilChanged()
            .onEach(uiStateFlow::emit)
            .launchIn(scope = viewModelScope)
    }

    private fun setupCommandsFlow(actor: Actor<Command, Event>) {
        actor.act(commandsFlow)
            .onEach(eventsFlow::emit)
            .launchIn(scope = viewModelScope)
    }

    private fun setupEventsFlow(reducer: Reducer<Command, Effect, Event, State>) {
        eventsFlow
            .map { event -> reducer.reduce(stateFlow.value, event) }
            .onEach { update -> update.state?.let { stateFlow.emit(it) } }
            .onEach { update -> update.commands.forEach { commandsFlow.emit(it) } }
            .onEach { update -> update.effects.forEach { effectsChannel.send(it) } }
            .launchIn(scope = viewModelScope)
    }

    private fun applyInitialEvents(initialEvents: List<Event>) {
        if (initialEvents.isEmpty()) return

        viewModelScope.launch {
            eventsFlow.emitAll(initialEvents.asFlow())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun State.toUiState(): UiState {
        return uiStateMapper?.map(this) ?: this as UiState
    }
}