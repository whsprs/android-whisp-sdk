package ai.whsprs.sdk.be.ws.model

data class WhispTextMessage(
    val id: String,
    val text: String,
    val status: Status
) {

    enum class Status {

        Processing,

        Completed,

        Failed
    }
}