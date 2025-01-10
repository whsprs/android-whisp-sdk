package ai.whsprs.sdk.client.model

import ai.whsprs.sdk.client.model.WhispSendEvent.TxHash

data class WhispSendTransactionTxHash(
    val data: Data,
    val type: WhispSendEvent = TxHash,
) {
    data class Data(
        val id: String, // Whisp transaction identifier
        val txHash: String // Transaction hash
    )

    companion object {
        fun create(id: String, txHash: String): WhispSendTransactionTxHash {
            return WhispSendTransactionTxHash(
                data = Data(id, txHash)
            )
        }
    }
}