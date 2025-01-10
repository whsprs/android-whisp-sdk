package ai.whsprs.sdk.be.chat.core.session

import java.time.OffsetDateTime

data class Session(
    val sessionId: String,
    val sessionType: String,
    val createdAt: OffsetDateTime?,
) {

    companion object {

        val NotInitialized = Session(
            sessionId = "",
            sessionType = "",
            createdAt = null,
        )
    }
}

inline val Session.isInitialized: Boolean
    get() = sessionId.isNotEmpty()