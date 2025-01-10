package ai.whsprs.sdk.be.chat.api.model.message

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class WhisperMessages(val messages: List<WhisperMessage>?)
