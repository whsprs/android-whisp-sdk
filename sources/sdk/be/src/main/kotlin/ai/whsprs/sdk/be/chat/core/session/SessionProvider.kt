package ai.whsprs.sdk.be.chat.core.session

import kotlinx.coroutines.flow.MutableStateFlow

interface SessionProvider {

    val session: MutableStateFlow<Session>
}

inline val SessionProvider.isSessionActive: Boolean
    get() = session.value.sessionId.isNotEmpty()

inline val SessionProvider.sessionId: String
    get() = session.value.sessionId

inline val SessionProvider.sessionType: String
    get() = session.value.sessionType
