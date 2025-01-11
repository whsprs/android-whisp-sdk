package ai.whsprs.sdk.ui.chat.presentation

import ai.whsprs.sdk.ui.components.messageinput.ChatInputState
import ai.whsprs.sdk.ui.components.messageinput.text_input.TextInputState
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.MessageListState
import ai.whsprs.sdk.ui.components.topappbar.ChatTopAppBarState

data class WhispChatUiState(
    val messageListState: MessageListState,
    val inputState: ChatInputState,
    val topAppBarState: ChatTopAppBarState,
) {

    val isInputVisible: Boolean
        get() = inputState !is ChatInputState.HiddenState

    val lastMessageKey: String
        get() = when (messageListState) {
            is MessageListState.Content -> messageListState
                .messages
                .sortedBy(ChatLazyItem::date)
                .map(ChatLazyItem::key)
                .lastOrNull()
                .orEmpty()
            else -> messageListState.key
        }

    companion object {

        val DefaultPreview = WhispChatUiState(
            messageListState = MessageListState.Default,
            inputState = ChatInputState.MessageInputState(
                textInputState = TextInputState.Default,
            ),
            topAppBarState = ChatTopAppBarState.Default,
        )

        val ContentPreview = WhispChatUiState(
            messageListState = MessageListState.Messages,
            inputState = ChatInputState.MessageInputState(
                textInputState = TextInputState.Default,
            ),
            topAppBarState = ChatTopAppBarState.Default,
        )
    }
}