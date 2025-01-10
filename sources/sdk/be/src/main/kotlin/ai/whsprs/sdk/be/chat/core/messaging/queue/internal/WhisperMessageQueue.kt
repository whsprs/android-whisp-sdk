package ai.whsprs.sdk.be.chat.core.messaging.queue.internal

import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.chat.core.messaging.message.isPending
import ai.whsprs.sdk.be.chat.core.messaging.queue.MessageQueue

class WhisperMessageQueue(
    private val coroutineContext: CoroutineContext,
) : MessageQueue {

    override val queue = MutableSharedFlow<List<Message<*>>>(replay = 1)

    private val internalQueue = CopyOnWriteArrayList<Message<*>>()

    override suspend fun add(message: Message<*>) {
        withContext(coroutineContext) {
            remove(message.id)

            // Always add pending message to the very end of the queue,
            // so there will be no collisions in client/server timestamps.
            if (message.isPending) {
                queue.emit(
                    internalQueue.sortedBy(Message<*>::date)
                        .plus(message)
                )
                internalQueue.add(message)
            } else {
                internalQueue.add(message)

                queue.emit(
                    internalQueue.sortedBy(Message<*>::date)
                )
            }
        }
    }

    override suspend fun addAll(messages: List<Message<*>>) {
        if (messages.isEmpty()) return

        withContext(coroutineContext) {
            val newMessageIds = messages.map(Message<*>::id)

            internalQueue.removeIf { message ->
                message.id in newMessageIds
            }

            messages
                .filterNot(internalQueue::contains)
                .let(internalQueue::addAll)

            queue.emit(internalQueue.sortedBy(Message<*>::date))
        }
    }

    override suspend fun remove(messageId: String) {
        withContext(coroutineContext) {
            val removed = internalQueue.removeIf { message ->
                message.id == messageId
            }

            if (removed) {
                queue.emit(internalQueue.sortedBy(Message<*>::date))
            }
        }
    }

    override suspend fun removeIf(predicate: (Message<*>) -> Boolean) {
        withContext(coroutineContext) {
            val removed = internalQueue.removeIf(predicate)

            if (removed) {
                queue.emit(internalQueue.sortedBy(Message<*>::date))
            }
        }
    }

    override suspend fun update(messageId: String, block: Message<*>.() -> Message<*>) {
        withContext(coroutineContext) {
            internalQueue.replaceAll { message ->
                if (message.id == messageId) {
                    block(message)
                } else {
                    message
                }
            }

            queue.emit(internalQueue.sortedBy(Message<*>::date))
        }
    }

    override suspend fun get(messageId: String): Message<*> {
        return withContext(coroutineContext) {
            internalQueue.first { message -> message.id == messageId }
        }
    }

    override suspend fun getLast(): Message<*>? {
        return withContext(coroutineContext) {
            internalQueue.lastOrNull()
        }
    }

    override suspend fun clear() {
        internalQueue.clear()
        queue.emit(emptyList())
    }
}