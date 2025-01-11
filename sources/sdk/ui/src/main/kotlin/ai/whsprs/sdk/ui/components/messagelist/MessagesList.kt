package ai.whsprs.sdk.ui.components.messagelist

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.rememberWhisperShimmer
import ai.whsprs.common.ui.style.Body16Regular
import ai.whsprs.common.ui.style.Heading3
import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import ai.whsprs.sdk.ui.animations.AnimatedContainerState
import ai.whsprs.sdk.ui.animations.rememberLazyListAnimationState
import ai.whsprs.sdk.ui.components.messagelist.MessageListState.Content
import ai.whsprs.sdk.ui.components.messagelist.MessageListState.FullScreenError
import ai.whsprs.sdk.ui.components.messagelist.MessageListState.FullScreenLoading
import ai.whsprs.sdk.ui.components.LoadingIndicator
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
internal fun MessagesList(
    messageListState: MessageListState,
    modifier: Modifier = Modifier,
    animatedContainerState: AnimatedContainerState = rememberLazyListAnimationState(),
    messagesLazyListState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(all = 16.dp),
    loadingContent: @Composable () -> Unit = { DefaultMessageListLoadingIndicator(modifier) },
    errorContent: @Composable (FullScreenError) -> Unit = { DefaultMessageListErrorContent(it, modifier) },
    loadingMoreContent: @Composable () -> Unit = { DefaultMessagesLoadingMoreIndicator() },
    onMessagesEndReached: (ChatLazyItem) -> Unit = {},
    onMessageClick: ((ChatLazyItem) -> Unit)? = null,
    onMessageLongClick: ((ChatLazyItem) -> Unit)? = null,
    onMessageListClick: () -> Unit = {},
    itemModifier: (previousItem: ChatLazyItem?, item: ChatLazyItem, nextItem: ChatLazyItem?) -> Modifier = MessagePaddingModifier,
    itemContent: @Composable (ChatLazyItem) -> Unit = { messageListItem ->
        MessageContainer(
            animatedContainerState = animatedContainerState,
            message = messageListItem,
            onClick = onMessageClick,
            onLongClick = onMessageLongClick,
        )
    },
) {
    AnimatedContent(
        targetState = messageListState,
        contentKey = MessageListState::key,
        contentAlignment = Alignment.Center,
        label = "Whisper Chat",
        transitionSpec = {
            fadeIn(
                animationSpec = tween(300)
            ) togetherWith fadeOut(animationSpec = tween(300))
        },
    ) { messageBoxState ->
        when (messageBoxState) {
            is FullScreenLoading -> loadingContent()
            is FullScreenError -> errorContent(messageBoxState)
            is Content -> Messages(
                modifier = modifier,
                messageListState = messageBoxState,
                messagesLazyListState = messagesLazyListState,
                loadingMoreContent = loadingMoreContent,
                contentPadding = contentPadding,
                onMessagesEndReached = onMessagesEndReached,
                itemContent = itemContent,
                itemModifier = itemModifier,
                onMessageListClick = onMessageListClick,
            )
        }
    }
}

@Composable
internal fun Messages(
    messageListState: Content,
    modifier: Modifier = Modifier,
    messagesLazyListState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(vertical = 16.dp),
    loadingMoreContent: @Composable () -> Unit = { DefaultMessagesLoadingMoreIndicator() },
    onMessagesEndReached: (ChatLazyItem) -> Unit = {},
    onMessageListClick: () -> Unit = {},
    itemContent: @Composable (ChatLazyItem) -> Unit,
    itemModifier: (previousItem: ChatLazyItem?, item: ChatLazyItem, nextItem: ChatLazyItem?) -> Modifier,
) {
    val messages = messageListState.messages
    val isLoadingMoreNewMessages = messageListState.isLoadingNewerMessages

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onMessageListClick
                ),
            state = messagesLazyListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = contentPadding,
            reverseLayout = true,
        ) {
            itemsIndexed(
                messages,
                key = { _, item -> item.key },
                contentType = { _, item -> item.contentType }
            ) { index, item ->
                val previousItem = messages.getOrNull(index - 1)
                val nextItem = messages.getOrNull(index + 1)
                val messageItemModifier = itemModifier(previousItem, item, nextItem)

                Box(modifier = messageItemModifier) {
                    itemContent(item)

                    if (!messageListState.isLoadingNewerMessages
                        && !messagesLazyListState.canScrollForward
                        && messagesLazyListState.isScrollInProgress
                    ) {
                        messages.lastOrNull()?.let(onMessagesEndReached)
                    }
                }
            }

            if (isLoadingMoreNewMessages) {
                item {
                    loadingMoreContent()
                }
            }
        }
    }
}

@Composable
private fun DefaultMessagesLoadingMoreIndicator() {
    LoadingIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    )
}

@Composable
private fun DefaultMessageListLoadingIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .shimmer(rememberWhisperShimmer())
            .padding(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp, end = ChatTheme.dimens.messageEndMargin)
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(WhisperTheme.Colors.Fill.Tertiary)
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp, end = ChatTheme.dimens.messageEndMargin)
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(WhisperTheme.Colors.Fill.Tertiary)
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp, end = ChatTheme.dimens.messageEndMargin)
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(WhisperTheme.Colors.Fill.Tertiary)
        )
    }
}

@Composable
private fun DefaultMessageListErrorContent(
    state: FullScreenError,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = state.title,
                style = Heading3,
                textAlign = TextAlign.Center,
                color = WhisperTheme.Colors.Text.Primary,
            )
            Text(
                text = state.subtitle,
                style = Body16Regular,
                textAlign = TextAlign.Center,
                color = WhisperTheme.Colors.Text.Secondary,
            )
        }
    }
}

@Composable
@WhisperPreview
private fun DefaultMessageListErrorContentPreview() {
    DefaultMessageListErrorContent(
        FullScreenError(
            title = "There is an error on our side",
            subtitle = "We're having trouble connecting your chat right now. Please try again later."
        )
    )
}

@Composable
@WhisperPreview
private fun DefaultMessageListLoadingContentPreview() {
    ChatTheme {
        DefaultMessageListLoadingIndicator()
    }
}
