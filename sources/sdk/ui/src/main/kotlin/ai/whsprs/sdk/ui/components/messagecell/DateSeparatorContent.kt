package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.style.Body12Regular
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.DateSeparator
import ai.whsprs.sdk.ui.theme.ChatPreview
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.OffsetDateTime
import java.util.UUID

@Composable
internal fun DateSeparatorContent(
    message: DateSeparator,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = message.text,
        style = Body12Regular,
        color = ChatTheme.colors.systemMessageColor,
    )
}

@Composable
@WhisperPreview
private fun DateSeparatorPreview() {
    ChatPreview {
        DateSeparatorContent(
            message = DateSeparator(
                key = UUID.randomUUID().toString(),
                date = OffsetDateTime.now(),
                text = "Today, 23 June"
            )
        )
    }
}