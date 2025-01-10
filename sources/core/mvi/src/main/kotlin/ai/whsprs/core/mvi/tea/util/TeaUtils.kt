package ai.whsprs.core.mvi.tea.util

import kotlinx.coroutines.flow.merge
import ai.whsprs.core.mvi.tea.component.Actor

internal fun <Command : Any, Event : Any> combineActors(
    actors: Set<Actor<Command, Event>>,
): Actor<Command, Event> {
    return Actor { commands ->
        actors
            .map { actor -> actor.act(commands) }
            .merge()
    }
}