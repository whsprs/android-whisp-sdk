@file:OptIn(ExperimentalFoundationApi::class)

package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.common.ui.markdown.WhisperMarkdown
import ai.whsprs.common.ui.style.Body16Regular
import ai.whsprs.sdk.ui.animations.bouncingClickable
import ai.whsprs.sdk.ui.components.messagelist.Align
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.Text
import ai.whsprs.sdk.ui.components.messagelist.DeliveryState.Received
import ai.whsprs.sdk.ui.components.messagelist.DeliveryState.Sent
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip

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
                    .padding(all = ChatTheme.dimens.messageInnerPadding)
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
                    textColor = ChatTheme.colors.whisperMessageBackgroundColor,
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