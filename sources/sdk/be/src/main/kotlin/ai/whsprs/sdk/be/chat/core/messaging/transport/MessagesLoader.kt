package ai.whsprs.sdk.be.chat.core.messaging.transport

import ai.whsprs.sdk.be.chat.core.messaging.message.Message


/**
 * Loading messages in the Whisper Chat.
 */
internal interface MessagesLoader {

    /**
     * Loads a page of messages.
     *
     * @param page  The page number to load.
     * @return      A list of messages for the specified page.
     * @throws Exception  If an error occurs during message loading.
     */
    suspend fun loadByPage(page: Int): List<Message<*>>

    /**
     * Loads replies to a specified message.
     *
     * @param messageId  The ID of the message to load replies for.
     * @return           An object containing the replies and status information.
     * @throws Exception If an error occurs during reply loading.
     */
    suspend fun loadReplies(messageId: String): MessageReplies
}

/**
 * Represents replies to a message.
 *
 * @property replies           A list of replies to the message.
 * @property completed         A flag indicating if client must stop polling.
 * @property sessionTerminated A flag indicating if the session has been terminated and must be restarted.
 */
data class MessageReplies(
    val replies: List<Message<*>>,
    val completed: Boolean,
    val sessionTerminated: Boolean,
)
