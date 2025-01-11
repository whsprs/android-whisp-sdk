package ai.whsprs.sdk.ui.components.messageinput

import ai.whsprs.sdk.ui.components.messageinput.text_input.TextInputState

sealed interface ChatInputState {

    data class MessageInputState(
        val textInputState: TextInputState,
    ) : ChatInputState

    data object HiddenState : ChatInputState
}
