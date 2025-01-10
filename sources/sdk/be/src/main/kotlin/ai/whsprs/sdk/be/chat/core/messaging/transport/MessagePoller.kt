package ai.whsprs.sdk.be.chat.core.messaging.transport

/**
 * Polling messages in the Whisper Chat.
 */
interface MessagePoller {

    /**
     * Polls for updates to a specified message.
     * Default polling interval is 3 seconds.
     *
     * @param messageId  The ID of the message to poll.
     * @throws Exception If an error occurs during the polling.
     */
    suspend fun poll(messageId: String)
}
