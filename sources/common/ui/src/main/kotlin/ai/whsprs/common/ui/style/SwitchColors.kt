package ai.whsprs.common.ui.style

import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun switchColors() = SwitchDefaults.colors(
    checkedThumbColor = WhisperTheme.Colors.Core.PrimaryB,
    checkedTrackColor = WhisperTheme.Colors.Core.Accent,
    uncheckedThumbColor = WhisperTheme.Colors.Core.PrimaryB,
    uncheckedTrackColor = WhisperTheme.Colors.Fill.Secondary,
    uncheckedBorderColor = Color.Transparent,
    disabledUncheckedTrackColor = WhisperTheme.Colors.Background.Base,
    disabledUncheckedThumbColor = WhisperTheme.Colors.Fill.Secondary,
    disabledUncheckedBorderColor = WhisperTheme.Colors.Border.Secondary,
    disabledCheckedThumbColor = WhisperTheme.Colors.Core.PrimaryB,
    disabledCheckedTrackColor = WhisperTheme.Colors.Core.Accent,
)
