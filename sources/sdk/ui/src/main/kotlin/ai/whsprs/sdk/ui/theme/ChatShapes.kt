package ai.whsprs.sdk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp


/**
 * Contains all the shapes for `Chat` components.
 *
 * @param myMessageBubble The bubble that wraps my message content.
 * @param whisperMessageBubble The bubble that wraps Chat message content.
 * @param inputField The shape of the input field.
 * @param bottomSheet The shape of components used as bottom sheets.
 */
@Immutable
data class ChatShapes(
    val myMessageBubble: Shape,
    val whisperMessageBubble: Shape,
    val defaultMessageBubble: Shape,
    val inputField: Shape,
    val bottomSheet: Shape,
) {
    companion object {

        fun defaultShapes(): ChatShapes = ChatShapes(
            myMessageBubble = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomEnd = 4.dp,
                bottomStart = 24.dp
            ),
            whisperMessageBubble = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 24.dp,
                bottomEnd = 24.dp,
                bottomStart = 24.dp
            ),
            defaultMessageBubble = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
                bottomEnd = 24.dp,
                bottomStart = 24.dp
            ),
            inputField = RoundedCornerShape(24.dp),
            bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        )
    }
}
