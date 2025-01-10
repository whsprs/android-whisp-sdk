package ai.whsprs.sdk.be.chat.core.messaging.message.mapper.internal

import kotlinx.serialization.json.Json
import ai.whsprs.sdk.be.chat.api.model.message.WhisperMessage
import ai.whsprs.sdk.be.chat.core.messaging.message.InformationalMessage
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.chat.core.messaging.message.TextMessage
import ai.whsprs.sdk.be.chat.core.messaging.message.mapper.MessageMapper

internal class WhisperMessageMapper(
    private val json: Json,
) : MessageMapper {

    override fun map(source: WhisperMessage): Message<*> {
        return when {
            source.isInformational -> source.buildInformationalMessage()
            else -> source.buildTextMessage()
        }
    }

    private fun WhisperMessage.buildTextMessage(): TextMessage {
        return TextMessage(
            id = id,
            data = content,
            date = createdAt,
            state = if (isReply) Message.State.Received else Message.State.Sent,
            reaction = when {
                isLiked == null -> Message.Reaction.None
                isLiked -> Message.Reaction.Liked
                else -> Message.Reaction.Disliked
            },
            isRead = isRead,
            isWhisper = isReply,
        )
    }

    private fun WhisperMessage.buildInformationalMessage(): InformationalMessage {
        return InformationalMessage(
            id = id,
            data = content,
            date = createdAt,
        )
    }
}