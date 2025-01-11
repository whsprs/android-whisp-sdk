package ai.whsprs.sdk.ui.components.whispericon


import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WhisperIcon(
    modifier: Modifier = Modifier,
    iconSize: Dp = 52.dp,
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = WhisperTheme.Colors.Text.PrimaryBlack,
                shape = CircleShape,
            )
            .size(iconSize),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ds_whisper),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .size(maxWidth * 0.6f)
        )
    }
}
