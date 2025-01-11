package ai.whsprs.sdk.ui.components.messagelist

import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.Text
import ai.whsprs.sdk.ui.components.messagelist.DeliveryState.Received
import androidx.compose.runtime.Stable
import java.time.OffsetDateTime
import java.util.UUID

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

        val Messages = Content(
            isLoadingNewerMessages = false,
            messages = listOf(
                Text(
                    key = UUID.randomUUID().toString(),
                    align = Align.Start,
                    date = OffsetDateTime.now(),
                    deliveryState = Received(),
                    position = Position.Top,
                    reactionsState = ReactionsState.Disabled,
                    animationSpec = LazyItemAnimationSpec.None,
                    text = "Hey there! I'm Whisp.\nLet's make web3 user-friendly."
                ),
                Text(
                    key = UUID.randomUUID().toString(),
                    align = Align.End,
                    date = OffsetDateTime.now(),
                    deliveryState = DeliveryState.Sent,
                    position = Position.Top,
                    reactionsState = ReactionsState.Disabled,
                    animationSpec = LazyItemAnimationSpec.None,
                    text = "Let's launch a new token on pump.fun."
                )
            ).reversed()
        )
    }
}
