package ai.whsprs.common.ui.markdown

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.style.Body16Regular
import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.MarkdownExtendedSpans
import com.mikepenz.markdown.model.markdownExtendedSpans

@Composable
fun WhisperMarkdown(
    modifier: Modifier = Modifier,
    text: String?,
    textColor: Color,
    textStyle: TextStyle,
    extendedSpans: MarkdownExtendedSpans? = null,
) {
    runCatching {
        Markdown(
            modifier = modifier,
            content = text.orEmpty(),
            colors = whisperMarkdownColors(textColor),
            typography = whisperMarkdownTypografy(textStyle),
            extendedSpans = extendedSpans ?: markdownExtendedSpans(),
        )
    }.onFailure {
        Text(
            modifier = modifier,
            text = text.orEmpty(),
            style = textStyle,
            color = textColor,
        )
    }
}
