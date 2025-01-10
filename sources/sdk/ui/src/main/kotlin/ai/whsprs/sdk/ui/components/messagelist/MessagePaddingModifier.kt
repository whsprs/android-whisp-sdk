package ai.whsprs.sdk.ui.components.messagelist

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal val MessagePaddingModifier: (ChatLazyItem?, ChatLazyItem, ChatLazyItem?) -> Modifier =
    { previousItem, item, nextItem ->
        val isFirstMessage = nextItem == null
        val isLastMessage = previousItem == null
        val authorChanged = item.align != nextItem?.align

        val topPadding = when {
            isFirstMessage -> 0.dp
            authorChanged -> 16.dp
            else -> 8.dp
        }
        val bottomPadding = when {
            isLastMessage -> 16.dp
            else -> 0.dp
        }

        Modifier.padding(top = topPadding, bottom = bottomPadding)
    }