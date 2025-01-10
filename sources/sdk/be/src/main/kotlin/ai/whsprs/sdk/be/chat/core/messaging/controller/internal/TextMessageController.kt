package ai.whsprs.sdk.be.chat.core.messaging.controller.internal

import ai.whsprs.sdk.be.chat.core.messaging.controller.MessageController
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.chat.core.messaging.message.TextMessage
import ai.whsprs.sdk.be.chat.core.messaging.queue.MessageQueue
import ai.whsprs.sdk.be.chat.core.messaging.transport.MessageRouter
import ai.whsprs.sdk.be.util.toOffsetDateTime
import ai.whsprs.sdk.be.util.withCurrentZoneAndOffset

internal class TextMessageController(
    private val messageQueue: MessageQueue,
    private val messageRouter: MessageRouter,
) : MessageController<TextMessage> {

    override suspend fun send(message: TextMessage): TextMessage {
        return runCatching { messageQueue.add(message) }
            .mapCatching { messageRouter.send(message) }
            .mapCatching { response ->
                message.copy(
                    id = response.messageId,
                    state = if (message.isWhisper) Message.State.Received else Message.State.Sent,
                    date = response.timestamp
                        .toOffsetDateTime()
                        .withCurrentZoneAndOffset(),
                )
            }
            .onSuccess { sent ->
                updateMessage(message.id) { sent }
            }
            .onFailure {
                updateMessage(message.id) {
                    copy(state = Message.State.FailedSend)
                }
            }
            .getOrThrow()
    }

    override suspend fun retrySend(messageId: String): TextMessage {
        return runCatching { messageQueue.get(messageId) as TextMessage }
            .mapCatching { message -> send(message) }
            .getOrThrow()
    }

    override suspend fun delete(messageId: String): TextMessage {
        return runCatching { messageQueue.remove(messageId) }
            .mapCatching { messageQueue.get(messageId) as TextMessage }
            .getOrThrow()
    }

    override suspend fun like(messageId: String): TextMessage {
        return runCatching { messageRouter.like(messageId) }
            .mapCatching {
                updateMessage(messageId) {
                    copy(reaction = Message.Reaction.Liked)
                }
            }
            .mapCatching { messageQueue.get(messageId) as TextMessage }
            .getOrThrow()
    }

    override suspend fun dislike(messageId: String): TextMessage {
        return runCatching { messageRouter.dislike(messageId) }
            .mapCatching {
                updateMessage(messageId) {
                    copy(reaction = Message.Reaction.Disliked)
                }
            }
            .mapCatching { messageQueue.get(messageId) as TextMessage }
            .getOrThrow()
    }

    private suspend fun updateMessage(id: String, block: TextMessage.() -> TextMessage) {
        messageQueue.update(id) {
            block((this as TextMessage))
        }
    }
}