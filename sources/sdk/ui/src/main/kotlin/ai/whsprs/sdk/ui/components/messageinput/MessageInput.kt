package ai.whsprs.sdk.ui.components.messageinput

import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.components.messageinput.ChatInputState.MessageInputState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import ai.whsprs.sdk.ui.components.messageinput.text_input.TextInput

@Composable
internal fun MessageInput(
    state: MessageInputState,
    modifier: Modifier = Modifier,
    onInputChange: (String) -> Unit = {},
    onCtaClick: () -> Unit = {},
) {
    val outerPaddingDp = 16.dp
    val buttonStartPaddingDp = 6.dp
    val buttonSizeDp = 48.dp

    val alpha by animateFloatAsState(
        targetValue = if (state.textInputState.isEnabled) 1f else .5f,
        label = "MessageInputAlphaAnimation"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = WhisperTheme.Colors.Background.Primary,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(outerPaddingDp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BoxWithConstraints {
                TextInput(
                    state = state.textInputState,
                    modifier = Modifier
                        .width(maxWidth - buttonSizeDp - buttonStartPaddingDp)
                        .alpha(alpha),
                    onInputChange = onInputChange,
                    onSendClick = onCtaClick,
                )
            }

            TextSendButton(
                onClick = onCtaClick,
                isEnabled = state.textInputState.isEnabled,
            )
        }
    }
}
