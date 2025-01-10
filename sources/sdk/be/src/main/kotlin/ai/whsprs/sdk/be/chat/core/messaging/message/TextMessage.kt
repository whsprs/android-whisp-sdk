package ai.whsprs.sdk.be.chat.core.messaging.message

import ai.whsprs.sdk.be.util.withCurrentZoneAndOffset
import java.time.OffsetDateTime
import java.util.UUID

data class TextMessage(
    override val id: String,
    override val data: String,
    override val state: Message.State,
    override val date: OffsetDateTime,
    override val reaction: Message.Reaction,
    override val isRead: Boolean,
    val isWhisper: Boolean,
) : Message<String> {

    companion object {

        fun assemble(
            text: String,
            isWhisper: Boolean = false,
        ): TextMessage {
            return TextMessage(
                id = UUID.randomUUID().toString(),
                data = text,
                state = if (isWhisper) Message.State.Received else Message.State.PendingSend,
                date = OffsetDateTime.now().withCurrentZoneAndOffset(),
                reaction = Message.Reaction.Disabled,
                isWhisper = isWhisper,
                isRead = true,
            )
        }
    }
}
