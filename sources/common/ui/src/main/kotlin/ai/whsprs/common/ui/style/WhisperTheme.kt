package ai.whsprs.common.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val whisperColorsPalette = lightColorScheme(
    primary = WhisperColorsLight.Core.Accent,
    error = WhisperColorsLight.Extension.Warning,
    background = WhisperColorsLight.Background.Base
)

private val whisperColorsPaletteDark = darkColorScheme(
    primary = WhisperColorsDark.Core.Accent,
    error = WhisperColorsDark.Extension.Warning,
    background = WhisperColorsDark.Background.Base
)

@Composable
fun WhisperTheme(
    colorScheme: ColorScheme = if (isSystemInDarkTheme()) whisperColorsPaletteDark else whisperColorsPalette,
    shapes: Shapes = MaterialTheme.shapes.copy(
        medium = RoundedCornerShape(16.dp),
        small = RoundedCornerShape(16.dp),
        extraSmall = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(16.dp),
    ),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        content = content
    )
}

object WhisperTheme {

    val Colors: WhisperColors
        @Composable
        get() = if (isSystemInDarkTheme()) WhisperColorsDark else WhisperColorsLight
}