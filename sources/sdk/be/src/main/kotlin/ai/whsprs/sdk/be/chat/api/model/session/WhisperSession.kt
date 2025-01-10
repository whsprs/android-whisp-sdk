package ai.whsprs.sdk.be.chat.api.model.session

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class WhisperSession(
    val sessionId: String,
    val sessionType: String,
)