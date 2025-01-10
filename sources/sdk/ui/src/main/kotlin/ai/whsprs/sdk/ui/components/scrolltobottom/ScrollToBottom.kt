package ai.whsprs.sdk.ui.components.scrolltobottom

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.R
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
internal fun ScrollToBottom(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                shape = CircleShape,
                color = WhisperTheme.Colors.Background.WhisperSecondary,
            )
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = WhisperTheme.Colors.Border.Secondary,
            )
            .size(56.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ds_arrow_down),
            tint = WhisperTheme.Colors.Text.Primary,
            contentDescription = "Scroll to bottom",
        )
    }
}

@WhisperPreview
@Composable
private fun ScrollToBottomPreview() {
    ChatTheme {
        ScrollToBottom()
    }
}