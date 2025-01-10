package ai.whsprs.sample.messageobserver

import ai.whsprs.sdk.Whispers
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import kotlinx.coroutines.flow.Flow

interface ObserveMessages {

    fun get(): Flow<List<Message<*>>>
}

private class ObserveWhisperChatMessages(
    private val whispers: Whispers,
) : ObserveMessages {

    override fun get(): Flow<List<Message<*>>> {
        return whispers.client.messages
    }
}