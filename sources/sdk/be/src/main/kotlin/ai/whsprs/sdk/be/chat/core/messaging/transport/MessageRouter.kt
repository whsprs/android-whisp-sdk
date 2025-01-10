package ai.whsprs.sdk.be.chat.core.messaging.transport

import ai.whsprs.sdk.be.chat.api.model.message.CreatedMessage
import ai.whsprs.sdk.be.chat.core.messaging.message.Message

/**
 * Sending or changing messages in the Whisper Chat.
 */
internal interface MessageRouter {

    /**
     * Sends a message.
     *
     * @param message  The message to be sent.
     * @return         The response after the message is sent.
     * @throws Exception If an error occurs during message sending.
     */
    suspend fun send(message: Message<*>): CreatedMessage

    /**
     * Likes a message.
     *
     * @param messageId  The ID of the message to like.
     * @throws Exception If an error occurs during the operation.
     */
    suspend fun like(messageId: String)

    /**
     * Dislikes a message.
     *
     * @param messageId  The ID of the message to dislike.
     * @throws Exception If an error occurs during the operation.
     */
    suspend fun dislike(messageId: String)
}
