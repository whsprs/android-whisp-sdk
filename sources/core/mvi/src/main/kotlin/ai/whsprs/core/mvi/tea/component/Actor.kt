package ai.whsprs.core.mvi.tea.component

import kotlinx.coroutines.flow.Flow

/**
 * Command handler. Load data, send side-effects.
 * Connect feature with repositories and business logic of application.
 * May contain business logic of feature.
 *
 * Function [act] accepts [Flow] of [Command]s
 *
 * Return [Flow] of [Event]s, for example LoadStarted, Success, Error etc.
 *
 *         ┌───────────────────┐
 *         │                   │
 * Command │                   │ Events
 * ────────►       Actor       ├───────►
 *         │                   │
 *         │                   │
 *         └───────────────────┘
 */
fun interface Actor<Command : Any, Event : Any> {

    fun act(commands: Flow<Command>): Flow<Event>
}