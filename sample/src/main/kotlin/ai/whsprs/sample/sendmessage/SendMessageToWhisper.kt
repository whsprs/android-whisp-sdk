package ai.whsprs.sample.sendmessage

import ai.whsprs.sdk.Whispers
import ai.whsprs.sdk.client.model.WhispSendTextMessage

interface SendMessageToWhisper {

    suspend fun send(text: String)
}

internal class SendMessageToWhisperImpl(
    private val whispers: Whispers,
) : SendMessageToWhisper {

    override suspend fun send(text: String) {
        runCatching {
            whispers.client.sendMessage(
                WhispSendTextMessage(data = WhispSendTextMessage.Data(text))
            )
        }
    }
}