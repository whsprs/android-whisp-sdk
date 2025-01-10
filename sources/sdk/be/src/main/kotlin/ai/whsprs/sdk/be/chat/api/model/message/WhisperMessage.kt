package ai.whsprs.sdk.be.chat.api.model.message

import androidx.annotation.Keep
import java.time.OffsetDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ai.whsprs.sdk.be.chat.api.model.message.MessageStatus.Informational
import ai.whsprs.sdk.be.util.OffsetDateTimeSerializer

@Keep
@Serializable
data class WhisperMessage(
    @SerialName("messageId")
    val id: String,
    @SerialName("repliedToMessageID")
    val repliedToMessageId: String? = null,
    val content: String,
    val isReply: Boolean,
    val status: MessageStatus = MessageStatus.Unknown,
    val isLiked: Boolean? = null,
    val isRead: Boolean,
    @Serializable(with = OffsetDateTimeSerializer::class) val createdAt: OffsetDateTime,
    @SerialName("unixTimestamp")
    val timestamp: Long,
) {

    val isInformational: Boolean
        get() = status == Informational
}

@Keep
@Serializable
enum class MessageStatus {

    Unknown,

    @SerialName("answered")
    Answered,

    @SerialName("answered_important")
    AnsweredImportant,

    @SerialName("technical")
    Technical,

    @SerialName("unanswered")
    Unanswered,

    @SerialName("asked")
    Asked,

    @SerialName("welcome")
    Welcome,

    @SerialName("session_terminated")
    SessionTerminated,

    @SerialName("informational")
    Informational,
}
