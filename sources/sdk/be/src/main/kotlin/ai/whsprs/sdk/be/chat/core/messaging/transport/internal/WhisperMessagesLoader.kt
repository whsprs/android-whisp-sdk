package ai.whsprs.sdk.be.chat.core.messaging.transport.internal

import ai.whsprs.sdk.be.chat.api.WhisperChatApi
import ai.whsprs.sdk.be.chat.api.model.message.MessageStatus.SessionTerminated
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.chat.core.messaging.message.mapper.MessageMapper
import ai.whsprs.sdk.be.chat.core.messaging.transport.MessageReplies
import ai.whsprs.sdk.be.chat.core.messaging.transport.MessagesLoader

internal class WhisperMessagesLoader(
    private val api: WhisperChatApi,
    private val messageMapper: MessageMapper,
) : MessagesLoader {

    override suspend fun loadByPage(page: Int): List<Message<*>> {
        return runCatching { api.getMessages(limit = PageSize, offset = PageSize * page) }
            .mapCatching { response -> response.messages.orEmpty() }
            .mapCatching { messages ->
                messages
                    .filterNot { message -> message.status == SessionTerminated }
                    .map(messageMapper::map)
            }
            .getOrThrow()
    }

    override suspend fun loadReplies(messageId: String): MessageReplies {
        return runCatching { api.getReplies(messageId) }
            .mapCatching { response ->
                val replies = listOfNotNull(response.messages.orEmpty() + response.originalMessage)
                    .flatten()
                    .filterNotNull()
                    .filterNot { message -> message.status == SessionTerminated }

                MessageReplies(
                    replies = replies.map(messageMapper::map),
                    completed = response.stop,
                    sessionTerminated = response.isSessionTerminated
                )
            }
            .getOrThrow()
    }

    companion object {
        const val PageSize = 20
    }
}
