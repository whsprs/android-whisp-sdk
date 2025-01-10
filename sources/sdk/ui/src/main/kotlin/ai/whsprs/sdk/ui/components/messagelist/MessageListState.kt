package ai.whsprs.sdk.ui.components.messagelist

import androidx.compose.runtime.Stable

sealed interface MessageListState {

    val key: String

    @Stable
    data object FullScreenLoading : MessageListState {
        override val key: String = "FullScreenLoading"
    }

    @Stable
    data class Content(
        val messages: List<ChatLazyItem>,
        val isLoadingNewerMessages: Boolean,
        override val key: String = "Content"
    ) : MessageListState

    @Stable
    data class FullScreenError(
        val title: String,
        val subtitle: String,
        override val key: String = "FullScreenError"
    ) : MessageListState

    companion object {

        val Default = FullScreenLoading
    }
}
