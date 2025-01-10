package ai.whsprs.sdk.client.model

enum class WhispSendEvent(val value: String) {
    TextMessage("text_message"),
    TxHash("tx_hash"),
    SignedTransaction("signed_transaction"),
    DeclineTransaction("decline_transaction");
}