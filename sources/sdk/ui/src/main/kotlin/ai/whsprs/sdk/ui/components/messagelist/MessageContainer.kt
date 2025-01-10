package ai.whsprs.sdk.ui.components.messagelist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ai.whsprs.sdk.ui.animations.AnimatedContainer
import ai.whsprs.sdk.ui.animations.AnimatedContainerState
import ai.whsprs.sdk.ui.animations.rememberLazyListAnimationState
import ai.whsprs.sdk.ui.components.messagecell.ConnectionErrorMessageContent
import ai.whsprs.sdk.ui.components.messagecell.DateSeparatorContent
import ai.whsprs.sdk.ui.components.messagecell.SystemMessageContent
import ai.whsprs.sdk.ui.components.messagecell.TextMessageContent
import ai.whsprs.sdk.ui.components.messagecell.TypingIndicatorContent
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.ConnectionError
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.DateSeparator
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.SystemMessage
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.Text
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.TypingIndicator
import ai.whsprs.sdk.ui.theme.ChatPreview

/**
 * Represents the message item container that allows us to customize each type of item in the MessageList.
 *
 */
@Composable
fun MessageContainer(
    message: ChatLazyItem,
    animatedContainerState: AnimatedContainerState,
    modifier: Modifier = Modifier,
    onClick: ((ChatLazyItem) -> Unit)? = null,
    onLongClick: ((ChatLazyItem) -> Unit)? = null,
) {
    AnimatedContainer(
        animatedContainerState = animatedContainerState,
        animationSpec = message.animationSpec,
        modifier = modifier,
        label = message.key,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = when (message.align) {
                Align.Start -> Alignment.Start
                Align.Center -> Alignment.CenterHorizontally
                Align.End -> Alignment.End
            }
        ) {
            MessageContent(
                message = message,
                modifier = modifier,
                onClick = onClick,
                onLongClick = onLongClick,
            )
        }
    }
}

@Composable
private fun MessageContent(
    message: ChatLazyItem,
    modifier: Modifier = Modifier,
    onClick: ((ChatLazyItem) -> Unit)? = null,
    onLongClick: ((ChatLazyItem) -> Unit)? = null,
) {
    var isActionMenuExpanded by remember { mutableStateOf(false) }

    when (message) {
        is ConnectionError -> ConnectionErrorMessageContent(
            message = message,
            modifier = modifier,
            onClick = onClick ?: {},
        )
        is DateSeparator -> DateSeparatorContent(message, modifier)
        is SystemMessage -> SystemMessageContent(message, modifier)
        is TypingIndicator -> TypingIndicatorContent(message, modifier)
        is Text -> TextMessageContent(
            message = message,
            modifier = modifier,
            onClick = onClick,
            onLongClick = { item ->
                if (onLongClick != null) {
                    onLongClick(item)
                }
            },
        )
    }
}

@Composable
@Preview
private fun MessageContainerPreview() {
    ChatPreview {
        MessageContainer(
            onClick = {},
            onLongClick = {},
            animatedContainerState = rememberLazyListAnimationState(),
            message = TypingIndicator,
        )
    }
}