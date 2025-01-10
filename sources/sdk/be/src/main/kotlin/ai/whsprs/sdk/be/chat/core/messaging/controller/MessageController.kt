package ai.whsprs.sdk.be.chat.core.messaging.controller

import ai.whsprs.sdk.be.chat.core.messaging.message.Message

/**
 * Basic interface used to operate with `Message` object.
 * Each method call will cooperate with `MessageQueue`,
 * notifying the subscribers
 */
interface MessageController<T : Message<*>> {

    suspend fun send(message: T): T

    suspend fun retrySend(messageId: String): T

    suspend fun delete(messageId: String): T

    suspend fun like(messageId: String): T

    suspend fun dislike(messageId: String): T
}