package ai.whsprs.common.ui.markdown

import ai.whsprs.common.ui.style.Heading1
import ai.whsprs.common.ui.style.Heading2
import ai.whsprs.common.ui.style.Heading3
import ai.whsprs.common.ui.style.Heading4
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography

fun whisperMarkdownColors(
    text: Color,
    codeText: Color? = null,
    inlineCodeText: Color = Color.Transparent,
    linkText: Color? = null,
    codeBackground: Color = Color.Transparent,
    inlineCodeBackground: Color = Color.Transparent,
    dividerColor: Color = Color.Transparent,
) = DefaultMarkdownColors(
    text = text,
    codeText = codeText ?: text,
    inlineCodeText = inlineCodeText,
    linkText = linkText ?: text,
    codeBackground = codeBackground,
    inlineCodeBackground = inlineCodeBackground,
    dividerColor = dividerColor
)

fun whisperMarkdownTypografy(
    text: TextStyle,
    h1: TextStyle = Heading1,
    h2: TextStyle = Heading2,
    h3: TextStyle = Heading3,
    h4: TextStyle = Heading4,
    h5: TextStyle = Heading4,
    h6: TextStyle = Heading4,
    code: TextStyle? = null,
    quote: TextStyle? = null,
    paragraph: TextStyle? = null,
    ordered: TextStyle? = null,
    bullet: TextStyle? = null,
    list: TextStyle? = null
) = DefaultMarkdownTypography(
    text = text,
    h1 = h1,
    h2 = h2,
    h3 = h3,
    h4 = h4,
    h5 = h5,
    h6 = h6,
    code = code ?: text,
    quote = quote ?: text,
    paragraph = paragraph ?: text,
    ordered = ordered ?: text,
    bullet = bullet ?: text,
    list = list ?: text
)