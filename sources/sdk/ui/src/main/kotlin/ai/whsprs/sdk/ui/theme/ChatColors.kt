package ai.whsprs.sdk.ui.theme

import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ChatColors(
    val chatPrimaryColor: Color,
    val chatAltColor: Color,
    val chatBackgroundColor: Color,
    val messageAltBackgroundColor: Color,
    val myMessageBackgroundColors: List<Color>,
    val myMessageTextColor: Color,
    val whisperMessageBackgroundColor: Color,
    val whisperMessageTextColor: Color,
    val systemMessageColor: Color,
    val defaultReactionBackgroundColor: Color,
    val defaultReactionIconColor: Color,
    val activeReactionBackgroundColor: Color,
    val activeReactionIconColor: Color,
) {
    companion object {

        @Composable
        fun defaultColors(): ChatColors = ChatColors(
            chatPrimaryColor = WhisperTheme.Colors.Feature.Whisper,
            chatAltColor = WhisperTheme.Colors.Fill.WhisperAlternate,
            chatBackgroundColor = WhisperTheme.Colors.Background.WhisperBase,
            messageAltBackgroundColor = WhisperTheme.Colors.Fill.WhisperAlternate,
            myMessageBackgroundColors = listOf(
                WhisperTheme.Colors.Feature.Whisper,
                Color(0xFFBD52FF)
            ),
            myMessageTextColor = WhisperTheme.Colors.Text.PrimaryWhite,
            whisperMessageBackgroundColor = WhisperTheme.Colors.Background.WhisperPrimary,
            whisperMessageTextColor = WhisperTheme.Colors.Text.Primary,
            systemMessageColor = WhisperTheme.Colors.Text.Secondary,
            defaultReactionBackgroundColor = WhisperTheme.Colors.Fill.Tertiary,
            defaultReactionIconColor = WhisperTheme.Colors.Fill.Secondary,
            activeReactionBackgroundColor = WhisperTheme.Colors.Feature.Whisper,
            activeReactionIconColor = WhisperTheme.Colors.Text.PrimaryWhite,
        )
    }
}
