package ai.whsprs.sdk.be.chat.core.session.internal

import ai.whsprs.sdk.be.chat.api.WhisperChatApi
import ai.whsprs.sdk.be.chat.api.model.session.CreateSession
import ai.whsprs.sdk.be.chat.core.session.Session
import ai.whsprs.sdk.be.chat.core.session.SessionController
import ai.whsprs.sdk.be.chat.core.session.SessionController.Companion.DefaultSessionType
import ai.whsprs.sdk.be.chat.core.session.SessionController.CreateSessionScenario
import ai.whsprs.sdk.be.chat.core.session.SessionProvider
import ai.whsprs.sdk.be.util.DateProvider

internal class WhisperSessionController(
    private val api: WhisperChatApi,
    private val dateProvider: DateProvider,
    private val sessionProvider: SessionProvider,
) : SessionController, SessionProvider by sessionProvider {

    override suspend fun createSession(
        sessionType: String,
        scenario: CreateSessionScenario,
    ): Session {
        return runCatching { buildSessionRequest() }
            .mapCatching { request -> api.createSession(request) }
            .mapCatching { session ->
                Session(
                    sessionId = session.sessionId,
                    sessionType = session.sessionType,
                    createdAt = dateProvider.currentDateTime,
                )
            }
            .onSuccess { newSession -> session.emit(newSession) }
            .getOrThrow()
    }

    override suspend fun restartSession(): Session {
        return createSession(
            sessionType = DefaultSessionType,
            scenario = CreateSessionScenario.Restart,
        )
    }

    private suspend fun buildSessionRequest(): CreateSession {
        return CreateSession(
            sessionType = DefaultSessionType,
        )
    }
}