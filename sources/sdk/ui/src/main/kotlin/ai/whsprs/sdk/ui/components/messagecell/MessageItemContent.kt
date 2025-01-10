package ai.whsprs.sdk.ui.components.messagecell

import ai.whsprs.sdk.ui.components.messagelist.Align
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.Position
import ai.whsprs.sdk.ui.components.messagelist.SizeSpec
import ai.whsprs.sdk.ui.theme.ChatTheme
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
internal fun MessageItemContent(
    message: ChatLazyItem,
    modifier: Modifier = Modifier,
    footerContent: @Composable () -> Unit = {},
    bubbleContent: @Composable (Shape) -> Unit = {},
) {
    val shape = when (message.position) {
        Position.Top -> when (message.align) {
            Align.Start -> ChatTheme.shapes.whisperMessageBubble
            Align.Center -> ChatTheme.shapes.defaultMessageBubble
            Align.End -> ChatTheme.shapes.myMessageBubble
        }

        else -> ChatTheme.shapes.defaultMessageBubble
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MessageBubble(
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .let { modifier ->
                    when (message.sizeSpec) {
                        SizeSpec.Fill -> modifier
                        else -> modifier.wrapContentWidth(
                            align = when (message.align) {
                                Align.Start -> Alignment.Start
                                Align.Center -> Alignment.CenterHorizontally
                                Align.End -> Alignment.End
                            }
                        )
                    }
                }
                .padding(
                    start = if (message.align == Align.End) 16.dp else 0.dp,
                    end = if (message.align == Align.Start) 16.dp else 0.dp,
                )
                .bubbleBackground(message, shape),
        ) {
            bubbleContent(shape)
        }

        footerContent()
    }
}

@SuppressLint("SuspiciousModifierThen")
@Stable
@Composable
private fun Modifier.bubbleBackground(
    message: ChatLazyItem,
    shape: Shape,
): Modifier {
    return this.then(
        when (message.align) {
            Align.Center -> this
            Align.Start -> background(
                color = ChatTheme.colors.whisperMessageBackgroundColor,
                shape = shape
            )

            Align.End -> background(
                brush = Brush.linearGradient(ChatTheme.colors.myMessageBackgroundColors),
                shape = shape,
            )
        }
    )
}