package ai.whsprs.sdk.ui.theme

import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ChatPreview(
    content: @Composable () -> Unit,
) {
    ChatTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WhisperTheme.Colors.Background.Primary)
        ) {
            content()
        }
    }
}