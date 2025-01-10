package ai.whsprs.sdk.be.chat.api.model.message

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CreatedMessage(
    val messageId: String,
    @SerialName("unixTimestamp")
    val timestamp: Long,
)