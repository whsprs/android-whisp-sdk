package ai.whsprs.common.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light",
    showBackground = true,
    backgroundColor = 0xFFF5F6F8, // WhisperColorsLight.Background.Base
)
@Preview(
    name = "Dark",
    showBackground = true,
    backgroundColor = 0xFF181A25, // WhisperColorsDark.Background.Base
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class WhisperPreview

@Preview(
    name = "Light",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF, // WhisperColorsLight.Background.Primary
)
@Preview(
    name = "Dark",
    showBackground = true,
    backgroundColor = 0xFF252736, // WhisperColorsDark.Background.Primary
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class WhisperPreviewPrimaryColors