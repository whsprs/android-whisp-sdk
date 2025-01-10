package ai.whsprs.sdk.be.chat.api.model.session

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class CreateSession(
    val sessionType: String,
)
