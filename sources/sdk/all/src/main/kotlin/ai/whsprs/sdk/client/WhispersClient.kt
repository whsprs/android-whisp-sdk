package ai.whsprs.sdk.client

import ai.whsprs.sdk.Environment
import ai.whsprs.sdk.be.chat.api.WhisperChatApi
import ai.whsprs.sdk.be.chat.api.model.connection.WhispInitializeRequestBody
import ai.whsprs.sdk.be.chat.core.messaging.message.Message
import ai.whsprs.sdk.be.ws.WhisperSocketServer
import ai.whsprs.sdk.be.ws.model.WhispSocketEvent
import ai.whsprs.sdk.be.ws.model.WhispSocketEvent.Disconnected
import ai.whsprs.sdk.be.ws.model.WhispSocketEvent.TextMessage
import ai.whsprs.sdk.be.ws.model.WhispSocketEvent.TransactionRequest
import ai.whsprs.sdk.client.model.WhispSendSignedTransaction
import ai.whsprs.sdk.client.model.WhispSendTextMessage
import ai.whsprs.sdk.client.model.WhispSendTransactionTxHash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

interface WhispersClient {

    val messages: Flow<List<Message<*>>>

    suspend fun connect()

    suspend fun sendMessage(message: WhispSendTextMessage)

    suspend fun signTransaction(tx: WhispSendSignedTransaction)

    suspend fun sendTransaction(txId: String, txHash: String)
}

internal class WhispersClientImpl(
    private val api: WhisperChatApi,
    private val apiKey: String,
    private val publicKey: String,
    private val environment: Environment,
    private val socketServer: WhisperSocketServer,
    private val json: Json,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
) : WhispersClient {

    override val messages = MutableSharedFlow<List<Message<*>>>()

    private var listenSocketEventsJob: Job? = null

    override suspend fun connect() {
        runCatching {
            socketServer.start(
                apiKey = apiKey,
                publicKey = publicKey,
                env = environment.rpcUrl,
            )
        }.mapCatching {
            api.initialize(
                WhispInitializeRequestBody(
                    connectionId = UUID.randomUUID().toString(),
                    parameters = mapOf(
                        "api_key" to apiKey,
                        "public_key" to publicKey,
                        "network" to environment.name,
                    )
                )
            )
        }.mapCatching {
            api.connect(apiKey)
        }.onSuccess {
            listenSocketEventsJob?.cancel()
            listenSocketEventsJob = socketServer.events
                .mapLatest { event ->
                    handleEvent(event)
                }
                .cancellable()
                .launchIn(scope)
        }
    }

    override suspend fun sendMessage(message: WhispSendTextMessage) {
        withContext(Dispatchers.IO) {
            runCatching {
                socketServer.send(
                    json.encodeToString(message)
                )
            }
        }
    }

    override suspend fun signTransaction(tx: WhispSendSignedTransaction) {
        withContext(Dispatchers.IO) {
            runCatching {
                socketServer.send(
                    json.encodeToString(tx)
                )
            }
        }
    }

    override suspend fun sendTransaction(txId: String, txHash: String) {
        withContext(Dispatchers.IO) {
            runCatching {
                socketServer.send(
                    json.encodeToString(
                        WhispSendTransactionTxHash.create(txId, txHash)
                    )
                )
            }
        }
    }

    private fun handleEvent(event: WhispSocketEvent) {
        when (event) {
            is TextMessage -> handleTextMessage(event)
            is TransactionRequest -> handleTransactionRequest(event)
            is Disconnected -> {
                listenSocketEventsJob?.cancel()
                listenSocketEventsJob = null
            }
        }
    }

    private fun handleTextMessage(event: TextMessage) {
        // To-Do
    }

    private fun handleTransactionRequest(event: TransactionRequest) {
        // To-Do
    }
}