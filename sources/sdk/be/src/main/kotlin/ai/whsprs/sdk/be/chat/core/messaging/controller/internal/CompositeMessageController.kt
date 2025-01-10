package ai.whsprs.sdk.be.chat.core.messaging.controller.internal

import ai.whsprs.sdk.be.chat.core.messaging.controller.MessageController
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.chat.core.messaging.message.Message.State.PendingSend
import ai.whsprs.sdk.be.chat.core.messaging.message.Message.State.PollingFailed
import ai.whsprs.sdk.be.chat.core.messaging.message.Message.State.Sent
import ai.whsprs.sdk.be.chat.core.messaging.message.TextMessage
import ai.whsprs.sdk.be.chat.core.messaging.queue.MessageQueue

internal class CompositeMessageController(
    private val messageQueue: MessageQueue,
    private val textMessageController: TextMessageController,
) : MessageController<Message<*>> {

    override suspend fun send(message: Message<*>): Message<*> {
        return when (message) {
            is TextMessage -> textMessageController.send(message)
            else -> error("Can not send message of type `${message::class}`.")
        }
    }

    override suspend fun retrySend(messageId: String): Message<*> {
        return messageQueue.get(messageId).let { message ->
            if (
                message.state in listOf(Sent, PendingSend, PollingFailed)
            ) return message

            when (message) {
                is TextMessage -> textMessageController.retrySend(messageId)
                else -> error("Can not resend message of type `${message::class}`.")
            }
        }
    }

    override suspend fun delete(messageId: String): Message<*> {
        return messageQueue.get(messageId).let { message ->
            when (message) {
                is TextMessage -> textMessageController.delete(messageId)
                else -> error("Can not delete message of type `${message::class}`.")
            }
        }
    }

    override suspend fun like(messageId: String): Message<*> {
        return messageQueue.get(messageId).let { message ->
            when (message) {
                is TextMessage -> textMessageController.like(messageId)
                else -> error("Can not like message of type `${message::class}`.")
            }
        }
    }

    override suspend fun dislike(messageId: String): Message<*> {
        return messageQueue.get(messageId).let { message ->
            when (message) {
                is TextMessage -> textMessageController.dislike(messageId)
                else -> error("Can not like message of type `${message::class}`.")
            }
        }
    }
}