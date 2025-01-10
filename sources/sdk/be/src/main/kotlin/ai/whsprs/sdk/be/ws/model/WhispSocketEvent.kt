package ai.whsprs.sdk.be.ws.model

import kotlinx.serialization.SerialName

sealed interface WhispSocketEvent {

    data class TextMessage(val content: WhispTextMessage) : WhispSocketEvent

    data class TransactionRequest(val content: WhispTransactionRequest) : WhispSocketEvent

    data object Disconnected : WhispSocketEvent

}

enum class WhispSocketEventType {

    @SerialName("text_message")
    TextMessage,

    @SerialName("transaction_request")
    TransactionRequest,
}