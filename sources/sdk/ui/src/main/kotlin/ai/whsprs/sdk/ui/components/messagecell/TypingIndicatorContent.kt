package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.sdk.ui.components.TypingIndicator
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.TypingIndicator
import ai.whsprs.sdk.ui.theme.ChatPreview
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun TypingIndicatorContent(
    message: TypingIndicator,
    modifier: Modifier = Modifier,
) {
    MessageItemContent(
        message = message,
        modifier = modifier,
    ) {
        TypingIndicator(
            modifier = Modifier
                .padding(all = ChatTheme.dimens.messageInnerPadding)
        )
    }
}

@Composable
@WhisperPreview
private fun TypingIndicatorContentPreview() {
    ChatPreview {
        TypingIndicatorContent(
            message = TypingIndicator
        )
    }
}