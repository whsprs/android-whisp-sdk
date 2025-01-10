package ai.whsprs.sdk.be.chat.core.messaging.message

import androidx.annotation.Keep
import java.time.OffsetDateTime
import kotlinx.serialization.Serializable

sealed interface Message<T : Any> {
    val id: String
    val data: T
    val state: State
    val reaction: Reaction
    val date: OffsetDateTime
    val isRead: Boolean

    @Keep
    @Serializable
    enum class State {

        /**
         * The message was created and being sent to server.
         */
        PendingSend,

        /**
         * The message was created and sent to server, but polling replies failed.
         */
        PollingFailed,

        /**
         * The message was created but failed to deliver.
         */
        FailedSend,

        /**
         * The message was created and successfully sent to server.
         */
        Sent,

        /**
         * The message was received from server,
         * but there are other replies from Whisper await.
         */
        ReceivedPartially,

        /**
         * The message was received from server, user is able to answer.
         */
        Received,
    }

    enum class Reaction { Disabled, None, Liked, Disliked }
}

internal inline fun Message<*>.withState(
    provider: (Message<*>) -> Message.State
): Message<*> {
    return when (this) {
        is TextMessage -> copy(state = provider(this))
        else -> this
    }
}

inline val Message<*>.isReceived: Boolean
    get() = state in listOf(Message.State.Received, Message.State.ReceivedPartially)

internal inline val Message<*>.isFailedToSend: Boolean
    get() = state in listOf(Message.State.FailedSend)

internal inline val Message<*>.isPending: Boolean
    get() = state in listOf(Message.State.PendingSend)
