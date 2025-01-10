package ai.whsprs.sdk.be.chat.api.model.message

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CreateMessage(
    val content: String,
    val sessionId: String,
    val messageType: MessageType,
) {

    @Serializable
    enum class MessageType {
        @SerialName("image")
        Image,

        @SerialName("text")
        Text
    }

    @Keep
    @Serializable
    data class ImageContent(
        val url: String,
    )
}