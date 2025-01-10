package ai.whsprs.core.mvi.tea.component

/**
 * Events handler. Contain presentation logic of feature.
 *
 * Function [reduce] accepts [State] and [Event]
 *
 * Return [Command]s, [Effect]s and [State]
 *
 * To update [State] - call `state` function.
 * After updating the state, it will be sent to the ui
 *
 * To send [Effect] - call `effects` function.
 * The effect will be sent to the UI as single live event
 *
 * To send [Command] - call `commands` function.
 * The command will be sent to the actor
 *
 *          ┌───────────────────┐State
 *  State   │                   ├───────►
 *  ────────►                   │Effects
 *  Event   │       Reducer     ├───────►
 *  ────────►                   │Commands
 *          │                   ├───────►
 *          └───────────────────┘
 */
interface Reducer<Command : Any, Effect : Any, Event : Any, State : Any> {

    fun reduce(currentState: State, event: Event): Update<Command, Effect, State>
}