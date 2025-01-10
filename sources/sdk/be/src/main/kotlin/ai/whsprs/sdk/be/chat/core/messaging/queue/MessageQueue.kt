package ai.whsprs.sdk.be.chat.core.messaging.queue

import kotlinx.coroutines.flow.SharedFlow
import ai.whsprs.sdk.be.chat.core.messaging.message.Message

/**
 * Basic interface for the MessageQueue.
 * MessageQueue is a storage for all messages
 * that are being sent and received in the Whisper Chat.
 *
 * Once message is added or updated in the queue it will affect the Chat State
 */
internal interface MessageQueue {

    val queue: SharedFlow<List<Message<*>>>

    suspend fun add(message: Message<*>)

    suspend fun addAll(messages: List<Message<*>>)

    suspend fun remove(messageId: String)

    suspend fun removeIf(predicate: (Message<*>) -> Boolean)

    suspend fun update(messageId: String, block: Message<*>.() -> Message<*>)

    suspend fun get(messageId: String): Message<*>

    suspend fun getLast(): Message<*>?

    suspend fun clear()
}
