package ai.whsprs.sdk.ui.components.messageinput

import ai.whsprs.common.ui.WhisperPreview
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
import ai.whsprs.sdk.ui.components.messageinput.text_input.TextInputState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size

@Composable
internal fun MessageInput(
    state: MessageInputState,
    modifier: Modifier = Modifier,
    onInputChange: (String) -> Unit = {},
    onCtaClick: () -> Unit = {},
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = WhisperTheme.Colors.Background.Primary)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextInput(
            state = state.textInputState,
            modifier = Modifier
                .weight(1f),
            onInputChange = onInputChange,
            onSendClick = onCtaClick,
        )

        TextSendButton(
            onClick = onCtaClick,
            isEnabled = state.textInputState.isEnabled,
        )
    }
}

@Composable
@WhisperPreview
private fun MessageInputPreview() {
    MessageInput(
        state = MessageInputState(
            textInputState = TextInputState.Default
        )
    )
}