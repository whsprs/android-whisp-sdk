package ai.whsprs.sdk.client.model

import ai.whsprs.sdk.client.model.WhispSendEvent.TextMessage

data class WhispSendTextMessage(
    val data: Data,
    val type: WhispSendEvent = TextMessage,
) {
    data class Data(
        val text: String
    )
}