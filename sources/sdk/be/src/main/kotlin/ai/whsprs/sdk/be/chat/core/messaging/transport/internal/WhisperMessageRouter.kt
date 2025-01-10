package ai.whsprs.sdk.be.chat.core.messaging.transport.internal

import ai.whsprs.sdk.be.chat.api.WhisperChatApi
import ai.whsprs.sdk.be.chat.api.model.message.CreateMessage
import ai.whsprs.sdk.be.chat.api.model.message.CreateMessage.MessageType.Text
import ai.whsprs.sdk.be.chat.api.model.message.CreatedMessage
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.chat.core.messaging.message.TextMessage
import ai.whsprs.sdk.be.chat.core.messaging.transport.MessageRouter
import ai.whsprs.sdk.be.chat.core.session.SessionController
import ai.whsprs.sdk.be.chat.core.session.isSessionActive
import ai.whsprs.sdk.be.chat.core.session.sessionId

internal class WhisperMessageRouter(
    private val api: WhisperChatApi,
    private val sessionController: SessionController,
) : MessageRouter {

    override suspend fun send(message: Message<*>): CreatedMessage {
        requireActiveSession()

        if (message !is TextMessage) {
            error("Only instances of `TextMessage` can be sent to chat.")
        }

        return runCatching { buildMessageRequest(message) }
            .mapCatching { request -> api.createMessage(request) }
            .getOrThrow()
    }

    override suspend fun like(messageId: String) {
        requireActiveSession()

        api.likeMessage(messageId)
    }

    override suspend fun dislike(messageId: String) {
        requireActiveSession()

        api.dislikeMessage(messageId)
    }

    private fun requireActiveSession() {
        check(sessionController.isSessionActive) {
            "Session is not active."
        }
    }

    private suspend fun buildMessageRequest(message: Message<*>): CreateMessage {
        val content = when (message) {
            is TextMessage -> message.data
            else -> ""
        }

        return CreateMessage(
            sessionId = sessionController.sessionId,
            messageType = Text,
            content = content,
        )
    }
}