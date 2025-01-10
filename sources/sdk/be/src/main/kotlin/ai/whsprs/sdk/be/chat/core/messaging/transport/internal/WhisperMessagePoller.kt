package ai.whsprs.sdk.be.chat.core.messaging.transport.internal

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import ai.whsprs.sdk.be.chat.core.messaging.message.Message.State.PollingFailed
import ai.whsprs.sdk.be.chat.core.messaging.message.Message.State.ReceivedPartially
import ai.whsprs.sdk.be.chat.core.messaging.message.isReceived
import ai.whsprs.sdk.be.chat.core.messaging.message.withState
import ai.whsprs.sdk.be.chat.core.messaging.queue.MessageQueue
import ai.whsprs.sdk.be.chat.core.messaging.transport.MessagePoller
import ai.whsprs.sdk.be.chat.core.messaging.transport.MessagesLoader
import ai.whsprs.sdk.be.chat.core.session.SessionController
import ai.whsprs.sdk.be.chat.core.session.isSessionActive
import ai.whsprs.sdk.be.chat.core.util.replaceAt
import ai.whsprs.sdk.be.util.Timer

internal class WhisperMessagePoller(
    private val messageQueue: MessageQueue,
    private val messagesLoader: MessagesLoader,
    private val sessionController: SessionController,
    private val scope: CoroutineScope,
) : MessagePoller {

    private var pollingJob: Job? = null

    override suspend fun poll(messageId: String) {
        check(sessionController.isSessionActive) {
            "Session is not active."
        }

        pollingJob?.cancel()

        pollingJob = scope.launch {
            Timer.endless(3.seconds).start()
                .cancellable()
                .collect {
                    runCatching { messagesLoader.loadReplies(messageId) }
                        .onFailure {
                            messageQueue.update(messageId) {
                                withState {
                                    PollingFailed
                                }
                            }

                            pollingJob?.cancel()
                        }
                        .mapCatching { polling ->
                            val replies = polling.replies
                            val lastReceivedMessage = replies
                                .indexOfLast { message -> message.isReceived }
                            val messages = replies.replaceAt(lastReceivedMessage) { lastMessage ->
                                    lastMessage.withState { message ->
                                        when {
                                            message.isReceived && !polling.completed -> ReceivedPartially
                                            else -> message.state
                                        }
                                    }
                                }

                            messageQueue.addAll(messages)

                            if (polling.sessionTerminated) {
                                restartSession()
                            }

                            if (polling.completed) {
                                pollingJob?.cancel()
                            }
                        }
                }
        }
    }

    private suspend fun restartSession() {
        runCatching { sessionController.restartSession() }
            .mapCatching { messagesLoader.loadByPage(page = 0) }
            .mapCatching { messages -> messageQueue.addAll(messages) }
    }
}