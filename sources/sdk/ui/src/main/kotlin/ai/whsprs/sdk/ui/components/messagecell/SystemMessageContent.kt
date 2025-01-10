package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.style.Body12Regular
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.SystemMessage
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.OffsetDateTime
import java.util.UUID

@Composable
internal fun SystemMessageContent(
    message: SystemMessage,
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
    ChatTheme {
        SystemMessageContent(
            message = SystemMessage(
                key = UUID.randomUUID().toString(),
                date = OffsetDateTime.now(),
                text = "Daily check-in started."
            )
        )
    }
}