package ai.whsprs.sdk.ui.components.messageinput

import ai.whsprs.sdk.ui.components.messageinput.text_input.TextInputState

sealed interface ChatInputState {

    data class MessageInputState(
        val textInputState: TextInputState,
    ) : ChatInputState {

        sealed interface InputButtonState {

            val id: String

            data object Text : InputButtonState {
                override val id: String
                    get() = "Text"
            }
        }
    }

    data object HiddenState : ChatInputState
}
