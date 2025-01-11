package ai.whsprs.sdk.ui.components.messageinput

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
internal fun TextSendButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isEnabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(
                color = WhisperTheme.Colors.Text.Primary,
            )
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ds_arrow_up),
            contentDescription = "Send button",
            tint = WhisperTheme.Colors.Text.PrimaryInverse,
        )
    }
}

@WhisperPreview
@Composable
private fun TextSendButtonPreview() {
    TextSendButton()
}