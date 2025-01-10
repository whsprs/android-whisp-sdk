package ai.whsprs.common.ui.components.button

import ai.whsprs.common.ui.style.Body16Medium
import ai.whsprs.common.ui.style.Body16SemiBold
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A composable function for creating a button with 48dp height.
 *
 * @param onClick The action to be performed when the button is clicked.
 * @param text The text to be displayed on the button.
 * @param style [WhisperButtonStyle] to be applied to the button.
 * @param modifier [Modifier] to be applied to the button.
 * @param shape The shape of the button, defaults to [RoundedCornerShape] with 12dp.
 * @param isTransparent Determines if the button has no background.
 * @param enabled Determines if the button is enabled or disabled.
 * @param iconStart An optional drawable resource to be displayed at the start of the button.
 * @param iconEnd An optional drawable resource to be displayed at the end of the button.
 *
 * Note: Do not set the height for this composable manually.
 */
@Composable
fun WhisperButton(
    onClick: () -> Unit,
    text: String,
    style: WhisperButtonStyle,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    isTransparent: Boolean = false,
    enabled: Boolean = true,
    @DrawableRes iconStart: Int? = null,
    @DrawableRes iconEnd: Int? = null
) {
    BaseButton(
        onClick = onClick,
        text = text,
        style = style,
        shape = shape,
        isTransparent = isTransparent,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = 24.dp,
        textStyle = Body16SemiBold,
        modifier = modifier
            .heightIn(min = 48.dp, max = 48.dp),
    )
}

/**
 * A composable function for creating a button with 40dp height.
 *
 * @param onClick The action to be performed when the button is clicked.
 * @param text The text to be displayed on the button.
 * @param style [WhisperButtonStyle] to be applied to the button.
 * @param modifier [Modifier] to be applied to the button.
 * @param shape The shape of the button, defaults to [RoundedCornerShape] with 12dp.
 * @param isTransparent Determines if the button has no background.
 * @param enabled Determines if the button is enabled or disabled.
 * @param iconStart An optional drawable resource to be displayed at the start of the button.
 * @param iconEnd An optional drawable resource to be displayed at the end of the button.
 *
 * Note: Do not set the height for this composable manually.
 */
@Composable
fun WhisperButtonMedium(
    onClick: () -> Unit,
    text: String,
    style: WhisperButtonStyle,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    isTransparent: Boolean = false,
    enabled: Boolean = true,
    @DrawableRes iconStart: Int? = null,
    @DrawableRes iconEnd: Int? = null
) {
    BaseButton(
        onClick = onClick,
        text = text,
        style = style,
        shape = shape,
        isTransparent = isTransparent,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = 24.dp,
        textStyle = Body16SemiBold,
        modifier = modifier
            .heightIn(min = 40.dp, max = 40.dp),
    )
}

/**
 * A composable function for creating a button with 32dp height.
 *
 * @param onClick The action to be performed when the button is clicked.
 * @param text The text to be displayed on the button.
 * @param style [WhisperButtonStyle] to be applied to the button.
 * @param modifier [Modifier] to be applied to the button.
 * @param shape The shape of the button, defaults to [RoundedCornerShape] with 12dp.
 * @param isTransparent Determines if the button has no background.
 * @param enabled Determines if the button is enabled or disabled.
 * @param iconStart An optional drawable resource to be displayed at the start of the button.
 * @param iconEnd An optional drawable resource to be displayed at the end of the button.
 *
 * Note: Do not set the height for this composable manually.
 */
@Composable
fun WhisperButtonSmall(
    onClick: () -> Unit,
    text: String,
    style: WhisperButtonStyle,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    isTransparent: Boolean = false,
    enabled: Boolean = true,
    @DrawableRes iconStart: Int? = null,
    @DrawableRes iconEnd: Int? = null
) {
    BaseButton(
        onClick = onClick,
        text = text,
        style = style,
        shape = shape,
        isTransparent = isTransparent,
        enabled = enabled,
        iconStart = iconStart,
        iconEnd = iconEnd,
        iconSize = 20.dp,
        textStyle = Body16Medium,
        modifier = modifier
            .heightIn(min = 32.dp, max = 32.dp),
    )
}

@Composable
private fun BaseButton(
    onClick: () -> Unit,
    text: String,
    style: WhisperButtonStyle,
    shape: Shape,
    isTransparent: Boolean,
    enabled: Boolean,
    @DrawableRes iconStart: Int?,
    @DrawableRes iconEnd: Int?,
    iconSize: Dp,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
) {
    with(if (isTransparent) style.transparent() else style.solid()) {
        Button(
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = contentColor,
                disabledContainerColor = disabledBackgroundColor,
                disabledContentColor = disabledContentColor
            ),
            shape = shape,
            border = borderColor?.let { BorderStroke(1.dp, it) },
            elevation = null,
            enabled = enabled,
            contentPadding = PaddingValues(
                start = if (iconStart != null) 8.dp else 12.dp,
                end = if (iconEnd != null) 8.dp else 12.dp,
            ),
        ) {
            if (iconStart != null) {
                Icon(
                    painter = painterResource(id = iconStart),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(iconSize)
                )
            }
            Text(
                text = text,
                style = textStyle.copy(textAlign = TextAlign.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (iconEnd != null) {
                Icon(
                    painter = painterResource(id = iconEnd),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(iconSize)
                )
            }
        }
    }
}
