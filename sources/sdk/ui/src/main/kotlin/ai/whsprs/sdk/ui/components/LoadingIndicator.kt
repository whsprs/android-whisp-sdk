package ai.whsprs.sdk.ui.components

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingIndicator(
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 24.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize),
            strokeWidth = 2.dp,
            color = ChatTheme.colors.chatPrimaryColor
        )
    }
}

@Composable
@WhisperPreview
private fun LoadingIndicatorPreview() {
    LoadingIndicator()
}