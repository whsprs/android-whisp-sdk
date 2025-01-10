package ai.whsprs.sdk.client.model

import ai.whsprs.sdk.client.model.WhispSendEvent.SignedTransaction


data class WhispSendSignedTransaction(
    val data: Data,
    val type: WhispSendEvent = SignedTransaction,
) {
    data class Data(
        val id: String, // Whisp transaction identifier
        val signedTransaction: String // Signed transaction (base64)
    )

    companion object {
        fun create(id: String, signedTransaction: String): WhispSendSignedTransaction {
            return WhispSendSignedTransaction(
                data = Data(id, signedTransaction)
            )
        }
    }
}
