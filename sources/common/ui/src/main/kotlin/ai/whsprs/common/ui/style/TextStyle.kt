package ai.whsprs.common.ui.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val Heading1 = textStyle(34.sp, FontWeight.Bold)
val Heading2 = textStyle(28.sp, FontWeight.Bold)
val Heading3 = textStyle(24.sp, FontWeight.Bold)
val Heading3Heavy = textStyle(24.sp, FontWeight.ExtraBold)
val Heading4 = textStyle(18.sp, FontWeight.SemiBold)

val Body18Regular = textStyle(18.sp, FontWeight.Normal)
val Body16SemiBold = textStyle(16.sp, FontWeight.SemiBold)
val Body16Medium = textStyle(16.sp, FontWeight.Medium)
val Body16Regular = textStyle(16.sp, FontWeight.Normal)
val Body14SemiBold = textStyle(14.sp, FontWeight.SemiBold)
val Body14Medium = textStyle(14.sp, FontWeight.Medium)
val Body14Regular = textStyle(14.sp, FontWeight.Normal)
val Body12Medium = textStyle(12.sp, FontWeight.Medium)
val Body12Regular = textStyle(12.sp, FontWeight.Normal)

fun textStyle(
    size: TextUnit,
    weight: FontWeight,
    fontFamily: FontFamily = robotoFamily,
    letterSpacing: TextUnit = TextUnit.Unspecified,
) = TextStyle(
    fontSize = size,
    fontFamily = fontFamily,
    fontWeight = weight,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    letterSpacing = letterSpacing
)

/**
 * Applies [TextDecoration] to current [TextStyle] without changing anything else
 */
fun TextStyle.applyDecoration(textDecoration: TextDecoration): TextStyle {
    return TextStyle(
        color = this.color,
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFamily = this.fontFamily,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        background = this.background,
        textDecoration = textDecoration,
        shadow = this.shadow,
        drawStyle = this.drawStyle,
        textAlign = this.textAlign,
        textDirection = this.textDirection,
        lineHeight = this.lineHeight,
        textIndent = this.textIndent,
        platformStyle = this.platformStyle,
        lineHeightStyle = this.lineHeightStyle,
        lineBreak = this.lineBreak,
        hyphens = this.hyphens,
        textMotion = this.textMotion,
    )
}

/**
 * Applies text color to current [TextStyle] without changing anything else
 */
fun TextStyle.applyColor(textColor: Color): TextStyle {
    return TextStyle(
        color = textColor,
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        fontStyle = this.fontStyle,
        fontSynthesis = this.fontSynthesis,
        fontFamily = this.fontFamily,
        fontFeatureSettings = this.fontFeatureSettings,
        letterSpacing = this.letterSpacing,
        baselineShift = this.baselineShift,
        textGeometricTransform = this.textGeometricTransform,
        localeList = this.localeList,
        background = this.background,
        textDecoration = this.textDecoration,
        shadow = this.shadow,
        drawStyle = this.drawStyle,
        textAlign = this.textAlign,
        textDirection = this.textDirection,
        lineHeight = this.lineHeight,
        textIndent = this.textIndent,
        platformStyle = this.platformStyle,
        lineHeightStyle = this.lineHeightStyle,
        lineBreak = this.lineBreak,
        hyphens = this.hyphens,
        textMotion = this.textMotion,
    )
}