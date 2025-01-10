package ai.whsprs.common.ui.components.button

import ai.whsprs.common.ui.style.WhisperColorsDark
import ai.whsprs.common.ui.style.WhisperColorsLight
import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

enum class WhisperButtonStyle {

    Primary,
    Secondary,
    System,
    TertiaryBordered,
    TertiaryFilled,
    TertiaryFilledDark,
    TertiaryFilledLight,
    TextPrimary,
    TextSecondary,
    WarningAlternate,
    WarningBordered;

    @Composable
    fun solid(): WhisperButtonColors = colors(isTransparent = false)

    @Composable
    fun transparent(): WhisperButtonColors = colors(isTransparent = true)

    @Composable
    private fun colors(isTransparent: Boolean): WhisperButtonColors = when (this) {
        Primary -> defaultButtonColors(
            backgroundColor = WhisperTheme.Colors.Fill.Accent,
            contentColor = WhisperTheme.Colors.Text.PrimaryWhite,
            isTransparent = isTransparent
        )

        Secondary -> defaultButtonColors(
            backgroundColor = WhisperTheme.Colors.Fill.AccentAlternate,
            contentColor = WhisperTheme.Colors.Text.Accent,
            isTransparent = isTransparent
        )

        TextPrimary -> defaultButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = WhisperTheme.Colors.Text.Primary,
            isTransparent = isTransparent
        )

        TextSecondary -> defaultButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = WhisperTheme.Colors.Text.Secondary,
            isTransparent = isTransparent
        )

        TertiaryBordered -> defaultButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = WhisperTheme.Colors.Text.Primary,
            borderColor = WhisperTheme.Colors.Border.Secondary,
            isTransparent = isTransparent
        )

        TertiaryFilled -> defaultButtonColors(
            backgroundColor = WhisperTheme.Colors.Fill.Tertiary,
            contentColor = WhisperTheme.Colors.Text.Tertiary,
            isTransparent = isTransparent
        )

        TertiaryFilledLight -> defaultButtonColors(
            backgroundColor = WhisperColorsLight.Fill.Tertiary,
            contentColor = WhisperColorsLight.Text.Tertiary,
            isTransparent = isTransparent
        )

        TertiaryFilledDark -> defaultButtonColors(
            backgroundColor = WhisperColorsDark.Fill.Tertiary,
            contentColor = WhisperTheme.Colors.Text.PrimaryWhite,
            isTransparent = isTransparent
        )

        WarningAlternate -> defaultButtonColors(
            backgroundColor = WhisperTheme.Colors.Fill.WarningAlternate,
            contentColor = WhisperTheme.Colors.Extension.Warning,
            isTransparent = isTransparent
        )

        System -> defaultButtonColors(
            backgroundColor = WhisperColorsDark.Background.SystemOverlay,
            contentColor = WhisperColorsDark.Text.PrimaryWhite,
            isTransparent = isTransparent
        )

        WarningBordered -> defaultButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = WhisperTheme.Colors.Extension.Warning,
            borderColor = WhisperTheme.Colors.Border.Secondary,
            isTransparent = isTransparent
        )
    }
}

@Composable
fun defaultButtonColors(
    backgroundColor: Color,
    contentColor: Color,
    disabledBackgroundColor: Color = WhisperTheme.Colors.Fill.Tertiary,
    disabledContentColor: Color = WhisperTheme.Colors.Text.Tertiary,
    borderColor: Color? = null,
    isTransparent: Boolean = false,
) = WhisperButtonColors(
    backgroundColor = if (isTransparent) Color.Transparent else backgroundColor,
    contentColor = contentColor,
    disabledBackgroundColor = disabledBackgroundColor,
    disabledContentColor = disabledContentColor,
    borderColor = borderColor
)

@Stable
data class WhisperButtonColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val disabledBackgroundColor: Color,
    val disabledContentColor: Color,
    val borderColor: Color?,
)
