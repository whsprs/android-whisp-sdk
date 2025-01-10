package ai.whsprs.core.mvi.tea.dsl

import ai.whsprs.core.mvi.tea.component.Update

internal class Updater<Command : Any, Effect : Any, State : Any>(
    currentState: State,
) {

    var state: State = currentState
        private set

    private val commands: MutableList<Command> = mutableListOf()
    private val effects: MutableList<Effect> = mutableListOf()

    fun state(block: State.() -> State) {
        state = block.invoke(state)
    }

    fun commands(vararg commands: Command) {
        this.commands += commands
    }

    fun effects(vararg effects: Effect) {
        this.effects += effects
    }

    internal fun collect(): Update<Command, Effect, State> {
        return Update(
            state = state,
            commands = commands,
            effects = effects,
        )
    }
}