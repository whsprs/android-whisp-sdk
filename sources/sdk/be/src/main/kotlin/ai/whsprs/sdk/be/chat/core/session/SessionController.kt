package ai.whsprs.sdk.be.chat.core.session

/**
 * Controls user sessions in the chat.
 */
interface SessionController : SessionProvider {

    /**
     * Creates a new session based on the provided parameters.
     *
     * @param sessionType     The type of session to be created
     * @param scenario        The scenario under which the session is being created.
     * @return                The newly created session.
     * @throws Exception      If an error occurs during session creation.
     */
    suspend fun createSession(
        sessionType: String,
        scenario: CreateSessionScenario,
    ): Session

    /**
     * Restarts the current session.
     *
     * Will be invoked upon receiving `sessionTerminated` while chat polling.
     *
     * @return                The restarted session.
     * @throws Exception      If an error occurs during session restart.
     */
    suspend fun restartSession(): Session

    /**
     * Enum representing the possible scenarios for creating a session.
     */
    enum class CreateSessionScenario {
        /** No specific scenario. */
        None,

        /**
         * Session is being restarted upon session terminating or chat history deletion.
         */
        Restart
    }

    companion object {

        const val DefaultSessionType = "general"
    }
}
