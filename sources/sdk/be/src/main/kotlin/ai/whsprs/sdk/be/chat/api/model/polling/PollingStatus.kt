package ai.whsprs.sdk.be.chat.api.model.polling

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class PollingStatus(val requests: List<String>?)