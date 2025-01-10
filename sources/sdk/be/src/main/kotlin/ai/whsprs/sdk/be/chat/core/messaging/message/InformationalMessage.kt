package ai.whsprs.sdk.be.chat.core.messaging.message

import java.time.OffsetDateTime

data class InformationalMessage(
    override val id: String,
    override val data: String,
    override val date: OffsetDateTime,
) : Message<String> {

    override val reaction: Message.Reaction = Message.Reaction.Disabled
    override val state: Message.State = Message.State.Received
    override val isRead: Boolean = true
}