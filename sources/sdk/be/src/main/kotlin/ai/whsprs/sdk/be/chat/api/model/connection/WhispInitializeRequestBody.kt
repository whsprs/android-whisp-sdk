package ai.whsprs.sdk.be.chat.api.model.connection

data class WhispInitializeRequestBody(
    val connectionId: String,
    val parameters: Map<String, String>
)