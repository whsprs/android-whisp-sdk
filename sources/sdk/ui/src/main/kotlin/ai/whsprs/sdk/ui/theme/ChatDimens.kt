package ai.whsprs.sdk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Immutable
data class ChatDimens(
    val messageMaxWidthFactor: Float,
    val messageEndMargin: Dp,
    val messageInnerPadding: Dp,
    val reactionSize: Dp,
) {
    companion object {

        @Composable
        fun defaultDimens(): ChatDimens = ChatDimens(
            messageMaxWidthFactor = .8f,
            messageEndMargin = 32.dp,
            messageInnerPadding = 16.dp,
            reactionSize = 32.dp,
        )
    }
}