package ai.whsprs.sdk.be.chat.core.messaging.message.mapper

import ai.whsprs.sdk.be.chat.api.model.message.WhisperMessage
import ai.whsprs.sdk.be.chat.core.messaging.message.Message

internal interface MessageMapper {

    fun map(source: WhisperMessage): Message<*>
}
