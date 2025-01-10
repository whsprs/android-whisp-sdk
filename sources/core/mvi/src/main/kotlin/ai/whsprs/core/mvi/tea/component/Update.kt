package ai.whsprs.core.mvi.tea.component

class Update<Command : Any, Effect : Any, State : Any>(
    val state: State? = null,
    val commands: List<Command> = emptyList(),
    val effects: List<Effect> = emptyList(),
)