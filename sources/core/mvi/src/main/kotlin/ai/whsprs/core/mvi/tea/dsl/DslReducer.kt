package ai.whsprs.core.mvi.tea.dsl

import ai.whsprs.core.mvi.tea.component.Reducer
import ai.whsprs.core.mvi.tea.component.Update

abstract class DslReducer<Command : Any, Effect : Any, Event : Any, State : Any> :
    Reducer<Command, Effect, Event, State> {

    private lateinit var updater: Updater<Command, Effect, State>

    val state: State
        get() = updater.state

    final override fun reduce(currentState: State, event: Event): Update<Command, Effect, State> {
        updater = Updater(currentState)
        reduce(event)
        return updater.collect()
    }

    protected abstract fun reduce(event: Event): Any?

    fun state(block: State.() -> State) = updater.state(block)

    fun commands(vararg commands: Command) = updater.commands(*commands)

    fun effects(vararg effects: Effect) = updater.effects(*effects)
}