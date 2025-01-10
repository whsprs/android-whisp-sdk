package ai.whsprs.sdk.be.chat.core.session.internal

import kotlinx.coroutines.flow.MutableStateFlow
import ai.whsprs.sdk.be.chat.core.session.Session
import ai.whsprs.sdk.be.chat.core.session.SessionProvider

internal class WhisperSessionProvider : SessionProvider {

    override val session = MutableStateFlow(value = Session.NotInitialized)
}
