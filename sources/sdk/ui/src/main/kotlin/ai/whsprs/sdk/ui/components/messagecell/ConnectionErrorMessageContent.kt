package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.components.button.WhisperButton
import ai.whsprs.common.ui.components.button.WhisperButtonStyle
import ai.whsprs.common.ui.style.Body16Regular
import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem.ConnectionError
import ai.whsprs.sdk.ui.components.messagelist.Position
import ai.whsprs.sdk.ui.theme.ChatPreview
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.OffsetDateTime
import java.util.UUID

@Composable
internal fun ConnectionErrorMessageContent(
    message: ConnectionError,
    modifier: Modifier = Modifier,
    onClick: (ChatLazyItem) -> Unit = {},
) {
    MessageItemContent(
        message = message,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .background(color = ChatTheme.colors.messageAltBackgroundColor)
                .padding(all = ChatTheme.dimens.messageInnerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = message.text,
                style = Body16Regular,
                color = WhisperTheme.Colors.Text.Primary,
                modifier = modifier
            )
            WhisperButton(
                text = message.cta,
                style = WhisperButtonStyle.WarningAlternate,
                onClick = { onClick(message) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            )
        }
    }
}

@Composable
@WhisperPreview
private fun ConnectionErrorMessageContentPreview() {
    ChatPreview {
        ConnectionErrorMessageContent(
            message = ConnectionError(
                position = Position.Top,
                date = OffsetDateTime.now(),
                animationSpec = LazyItemAnimationSpec.None,
                text = "Something went wrong, please try again later.",
                cta = "Retry",
                ctaMetadata = UUID.randomUUID().toString(),
            )
        )
    }
}