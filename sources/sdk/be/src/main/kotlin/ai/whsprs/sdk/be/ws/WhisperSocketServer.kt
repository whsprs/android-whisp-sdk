package ai.whsprs.sdk.be.ws

import ai.whsprs.sdk.be.ws.model.WhispSocketEvent
import ai.whsprs.sdk.be.ws.model.WhispSocketEvent.TextMessage
import ai.whsprs.sdk.be.ws.model.WhispSocketEvent.TransactionRequest
import ai.whsprs.sdk.be.ws.model.WhispSocketEventType
import ai.whsprs.sdk.be.ws.model.WhispTextMessage
import ai.whsprs.sdk.be.ws.model.WhispTransactionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

interface WhisperSocketServer {

    val events: Flow<WhispSocketEvent>

    suspend fun start(apiKey: String, publicKey: String, env: String)

    suspend fun send(data: String)
}

class WhisperSocketServerImpl(
    private val url: String,
    private val client: OkHttpClient,
    private val json: Json,
) : WhisperSocketServer {

    override val events = MutableSharedFlow<WhispSocketEvent>()

    private var webSocket: WebSocket? = null

    override suspend fun start(apiKey: String, publicKey: String, env: String) {
        webSocket = null

        runCatching { Request.Builder().url(url).build() }
            .mapCatching { request ->
                client.newWebSocket(request, object : WebSocketListener() {
                    override fun onMessage(webSocket: WebSocket, text: String) {
                        runCatching { parseRawEvent(text) }
                            .mapCatching { event -> events.tryEmit(event) }
                    }

                    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                        webSocket.close(code, reason)
                    }

                    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                        webSocket.close(SocketCloseErrorCode, null)
                        events.tryEmit(WhispSocketEvent.Disconnected)
                    }
                })
            }
            .onSuccess { webSocket = it }
    }

    override suspend fun send(data: String) {
        withContext(Dispatchers.IO) {
            runCatching { webSocket?.send(data) }
        }
    }

    private fun parseRawEvent(raw: String): WhispSocketEvent {
        return json.parseToJsonElement(raw).let { content ->
            val rawType = content.jsonObject["type"].toString()
            val rawData = content.jsonObject["data"].toString()
            val type = json.decodeFromString<WhispSocketEventType>(rawType)

            when (type) {
                WhispSocketEventType.TextMessage -> TextMessage(
                    content = json.decodeFromString<WhispTextMessage>(rawData)
                )
                WhispSocketEventType.TransactionRequest -> TransactionRequest(
                    content = json.decodeFromString<WhispTransactionRequest>(rawData)
                )
            }
        }
    }

    private companion object {

        private const val SocketCloseErrorCode = 1002
    }
}