@file:OptIn(ExperimentalFoundationApi::class)

package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.markdown.WhisperMarkdown
import ai.whsprs.common.ui.style.Body16Regular
import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec
import ai.whsprs.sdk.ui.animations.bouncingClickable
import ai.whsprs.sdk.ui.components.messagelist.Align
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.Text
import ai.whsprs.sdk.ui.components.messagelist.DeliveryState.Received
import ai.whsprs.sdk.ui.components.messagelist.DeliveryState.Sent
import ai.whsprs.sdk.ui.components.messagelist.Position
import ai.whsprs.sdk.ui.components.messagelist.ReactionsState
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.time.OffsetDateTime
import java.util.UUID

@Composable
internal fun TextMessageContent(
    message: Text,
    modifier: Modifier = Modifier,
    onClick: ((ChatLazyItem) -> Unit)? = null,
    onLongClick: ((ChatLazyItem) -> Unit)? = null,
) {
    val isSyncedWithServer = message.deliveryState in listOf(
        Sent,
        Received(waitingForMoreReplies = false),
        Received(waitingForMoreReplies = true),
    )

    MessageItemContent(
        message = message,
        modifier = modifier
            .bouncingClickable(
                enabled = onClick != null || onLongClick != null,
                onClick = { if (onClick != null) onClick(message) },
                onLongClick = {
                    if (onLongClick != null) {
                        onLongClick(message)
                    }
                },
            ),
        bubbleContent = { shape ->
            TextMessageBubbleContent(
                modifier = Modifier
                    .clip(shape)
                    .padding(
                        all = when (message.align) {
                            Align.Start -> 0.dp
                            else -> ChatTheme.dimens.messageInnerPadding
                        }
                    )
                    .alpha(if (isSyncedWithServer) 1f else .5f),
                message = message,
            )
        },
    )
}

@Composable
private fun TextMessageBubbleContent(
    message: Text,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (message.align) {
            Align.Start -> {
                WhisperMarkdown(
                    text = message.text,
                    textStyle = Body16Regular,
                    textColor = WhisperTheme.Colors.Text.Primary,
                )
            }

            Align.End -> Text(
                text = message.text,
                style = Body16Regular,
                color = ChatTheme.colors.myMessageTextColor,
            )

            Align.Center -> error("`Align.Center` is not supported for Text Messages.")
        }
    }
}

@Composable
@WhisperPreview
private fun WhisperTextMessageContentPreview() {
    ChatTheme {
        TextMessageContent(
            message = Text(
                key = UUID.randomUUID().toString(),
                align = Align.Start,
                date = OffsetDateTime.now(),
                deliveryState = Received(),
                position = Position.Top,
                reactionsState = ReactionsState.Disabled,
                animationSpec = LazyItemAnimationSpec.None,
                text = "Hey there! I'm Whisp.\nLet's make web3 user-friendly."
            )
        )
    }
}

@Composable
@WhisperPreview
private fun MyTextMessageContentPreview() {
    ChatTheme {
        TextMessageContent(
            message = Text(
                key = UUID.randomUUID().toString(),
                align = Align.End,
                date = OffsetDateTime.now(),
                deliveryState = Sent,
                position = Position.Top,
                reactionsState = ReactionsState.Disabled,
                animationSpec = LazyItemAnimationSpec.None,
                text = "Let's launch a token on pump.fun"
            )
        )
    }
}