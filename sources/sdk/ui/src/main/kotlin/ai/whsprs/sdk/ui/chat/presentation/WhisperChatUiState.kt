package ai.whsprs.sdk.ui.chat.presentation

import ai.whsprs.sdk.ui.components.messageinput.ChatInputState
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.MessageListState
import ai.whsprs.sdk.ui.components.topappbar.ChatTopAppBarState

data class WhisperChatUiState(
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
}