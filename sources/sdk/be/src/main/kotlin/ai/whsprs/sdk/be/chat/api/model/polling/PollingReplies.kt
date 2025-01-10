package ai.whsprs.sdk.be.chat.api.model.polling

import androidx.annotation.Keep
import kotlinx.serialization.Serializable
import ai.whsprs.sdk.be.chat.api.model.message.WhisperMessage

@Keep
@Serializable
data class PollingReplies(
    val stop: Boolean,
    val isSessionTerminated: Boolean,
    val messages: List<WhisperMessage>?,
    val originalMessage: WhisperMessage?,
)