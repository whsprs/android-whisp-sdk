package ai.whsprs.sdk.ui.components.messagecell

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Wraps the content of a message in a bubble.
 *
 * @param color The color of the bubble.
 * @param shape The shape of the bubble.
 * @param modifier Modifier for styling.
 * @param border The optional border of the bubble.
 * @param content The content of the message.
 */
@Composable
internal fun MessageBubble(
    shape: Shape,
    modifier: Modifier = Modifier,
    color: Color = Color.Transparent,
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        border = border,
    ) {
        content()
    }
}