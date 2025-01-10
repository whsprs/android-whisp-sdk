package ai.whsprs.sdk.ui.chat

import ai.whsprs.sdk.ui.animations.AnimatedContainerState
import ai.whsprs.sdk.ui.animations.rememberLazyListAnimationState
import ai.whsprs.sdk.ui.chat.presentation.WhisperChatUiState
import ai.whsprs.sdk.ui.components.messageinput.ChatInputState
import ai.whsprs.sdk.ui.components.messageinput.MessageInput
import ai.whsprs.sdk.ui.components.messagelist.ChatLazyItem
import ai.whsprs.sdk.ui.components.messagelist.MessagesList
import ai.whsprs.sdk.ui.components.scrolltobottom.ScrollToBottom
import ai.whsprs.sdk.ui.components.topappbar.ChatTopAppBar
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.LifecycleStartEffect
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WhisperChatScreen(
    state: WhisperChatUiState,
    messagesLazyListState: LazyListState = rememberLazyListState(),
    animatedContainerState: AnimatedContainerState = rememberLazyListAnimationState(),
    onMessagesEndReached: (ChatLazyItem) -> Unit = {},
    onMessageInputCtaClick: () -> Unit = {},
    onMessageInputChange: (String) -> Unit = {},
    onMessageClick: (ChatLazyItem) -> Unit = {},
    onMessageLongClick: (ChatLazyItem) -> Unit = {},
    onStart: () -> Unit = {},
    onPause: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    onMessageListClick: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val enableScrollToBottom by remember {
        derivedStateOf {
            messagesLazyListState.firstVisibleItemIndex > 0
        }
    }

    BackHandler { onBackPressed() }

    LaunchedEffect(state.lastMessageKey) {
        delay(250.milliseconds)
        messagesLazyListState.animateScrollToItem(0)
    }

    LifecycleStartEffect(Unit) {
        onStart()

        onStopOrDispose {
            onPause()
        }
    }

    ChatTheme {
        Scaffold(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.ime.exclude(WindowInsets.navigationBars))
                .fillMaxSize(),
            topBar = {
                ChatTopAppBar(
                    state = state.topAppBarState,
                    onBackPressed = onBackPressed,
                )
            },
            floatingActionButton = {
                ScrollToBottomButton(
                    isVisible = enableScrollToBottom,
                    onClick = {
                        coroutineScope.launch {
                            messagesLazyListState.scrollToItem(0)
                        }
                    },
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = state.isInputVisible,
                    enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
                    exit = slideOutVertically(tween(durationMillis = 800)),
                ) {
                    AnimatedContent(
                        targetState = state.inputState,
                        contentKey = ChatInputState::id,
                        transitionSpec = {
                            val durationMs = 300
                            (fadeIn(tween(durationMs, durationMs / 2)) +
                                    slideInVertically(tween(durationMs, durationMs / 2)) { fullHeight -> fullHeight })
                                .togetherWith(
                                    fadeOut(tween(durationMs)) +
                                            slideOutVertically(tween(durationMs)) { fullHeight -> fullHeight }
                                )
                        },
                        label = "ChatInputState",
                    ) { inputState ->
                        when (inputState) {
                            is ChatInputState.MessageInputState -> MessageInput(
                                state = inputState,
                                onInputChange = onMessageInputChange,
                                onCtaClick = onMessageInputCtaClick,
                            )

                            else -> Unit
                        }
                    }
                }
            },
            containerColor = ChatTheme.colors.chatBackgroundColor,
        ) { paddingValues ->
            MessagesList(
                messageListState = state.messageListState,
                animatedContainerState = animatedContainerState,
                modifier = Modifier
                    .padding(paddingValues),
                messagesLazyListState = messagesLazyListState,
                onMessagesEndReached = onMessagesEndReached,
                onMessageClick = onMessageClick,
                onMessageLongClick = onMessageLongClick,
                onMessageListClick = onMessageListClick,
            )
        }
    }
}

@Composable
private fun ScrollToBottomButton(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = isVisible,
        modifier = modifier,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut(),
    ) {
        ScrollToBottom(onClick = onClick)
    }
}