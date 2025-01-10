package ai.whsprs.sdk

import ai.whsprs.sdk.be.chat.api.WhisperChatApi
import ai.whsprs.sdk.be.ws.WhisperSocketServerImpl
import ai.whsprs.sdk.client.WhispersClient
import ai.whsprs.sdk.client.WhispersClientImpl
import ai.whsprs.sdk.env.WhispersEnvironment
import android.content.Context
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration


/**
 * Represents the Solana network environment the SDK will interact with.
 * Each environment provides its specific configuration, such as RPC endpoints.
 */
sealed class Environment(val name: String, val rpcUrl: String) {
    /**
     * Solana Mainnet Beta: the live production network.
     */
    object Mainnet : Environment("Mainnet Beta", "https://api.mainnet-beta.solana.com")

    /**
     * Solana Devnet: a testing environment for developers.
     */
    object Devnet : Environment("Devnet", "https://api.devnet.solana.com")

    /**
     * Solana Testnet: a public testing network with a closer configuration to Mainnet.
     */
    object Testnet : Environment("Testnet", "https://api.testnet.solana.com")
}

/**
 * Defines the logging level for the SDK. Determines the verbosity of logs.
 */
enum class LogLevel {
    /**
     * Disables all logging.
     */
    NONE,

    /**
     * Logs only errors.
     */
    ERROR,

    /**
     * Logs warnings and errors.
     */
    WARN,

    /**
     * Logs general information, warnings, and errors.
     */
    INFO
}

/**
 * Core class of the Whispers SDK. Handles initialization and provides
 * global configuration for the SDK instance.
 *
 * Includes settings for API authentication, public key, Solana environment,
 * and logging behavior.
 */
class Whispers private constructor(builder: Builder) {

    private val json by lazy(NONE) {
        Json {
            encodeDefaults = true
        }
    }

    private val okhttp by lazy(NONE) {
        OkHttpClient.Builder()
            .connectTimeout(10.seconds.toJavaDuration())
            .writeTimeout(10.seconds.toJavaDuration())
            .build()
    }

    private val retrofit by lazy(NONE) {
        Retrofit.Builder()
            .baseUrl(WhispersEnvironment.baseUrl)
            .client(okhttp)
            .build()
    }

    private val api by lazy(NONE) {
        retrofit.create<WhisperChatApi>()
    }

    private val socketServer by lazy(NONE) {
        WhisperSocketServerImpl(
            url = WhispersEnvironment.socketUrl,
            client = okhttp,
            json = json,
        )
    }

    val client: WhispersClient by lazy(NONE) {
        WhispersClientImpl(
            api = api,
            apiKey = apiKey,
            publicKey = publicKey,
            environment = environment,
            socketServer = socketServer,
            json = json,
        )
    }

    // Required parameters
    private val apiKey: String
    private val publicKey: String

    // Optional parameters
    private val environment: Environment
    private val logLevel: LogLevel

    init {
        this.apiKey = builder.apiKey
        this.publicKey = builder.publicKey
        this.environment = builder.environment
        this.logLevel = builder.logLevel

        if (logLevel >= LogLevel.INFO) {
            log("Whispers SDK initialized with environment: ${environment.name} (RPC URL: ${environment.rpcUrl})")
        }
    }

    /**
     * Returns the current Solana network environment.
     *
     * @return the [Environment] value indicating the active network.
     */
    fun getCurrentEnvironment(): Environment = environment

    /**
     * Returns the API key associated with the SDK.
     *
     * @return the API key as a [String].
     */
    fun getApiKey(): String = apiKey

    /**
     * Returns the public key associated with the SDK.
     *
     * @return the public key as a [String].
     */
    fun getPublicKey(): String = publicKey

    /**
     * Logs a message at the specified log level.
     * Logs are only output if the current log level permits it.
     *
     * @param message the message to be logged.
     */
    private fun log(message: String) {
        if (logLevel != LogLevel.NONE) {
            with(Timber.tag("Whispers")) {
                when (logLevel) {
                    LogLevel.WARN -> w(message)
                    LogLevel.ERROR -> e(message)
                    LogLevel.INFO -> i(message)
                    else -> Unit
                }
            }
        }
    }

    /**
     * Builder class for constructing [Whispers] instances.
     * Ensures proper validation and initialization of required fields.
     */
    class Builder() {
        // Required fields
        lateinit var apiKey: String
            private set
        lateinit var publicKey: String
            private set

        // Optional fields with default values
        var environment: Environment = Environment.Mainnet
            private set
        var logLevel: LogLevel = LogLevel.NONE
            private set

        /**
         * Sets the API key for SDK authentication.
         *
         * @param apiKey the API key as a [String].
         * @return the current [Builder] instance.
         */
        fun setApiKey(apiKey: String) = apply {
            this.apiKey = apiKey
        }

        /**
         * Sets the public key to be associated with the SDK instance.
         *
         * @param publicKey the public key as a [String].
         * @return the current [Builder] instance.
         */
        fun setPublicKey(publicKey: String) = apply {
            this.publicKey = publicKey
        }

        /**
         * Sets the Solana environment for the SDK. Determines the blockchain network
         * configuration (e.g., RPC endpoints).
         *
         * @param env the [Environment] value.
         * @return the current [Builder] instance.
         */
        fun setEnvironment(env: Environment) = apply {
            this.environment = env
        }

        /**
         * Sets the logging level for the SDK. Controls the verbosity of logs.
         *
         * @param level the desired [LogLevel].
         * @return the current [Builder] instance.
         */
        fun setLogLevel(level: LogLevel) = apply {
            this.logLevel = level
        }

        /**
         * Builds and returns a configured [Whispers] instance.
         * Ensures that all required fields are initialized.
         *
         * @return a fully configured [Whispers] instance.
         * @throws IllegalStateException if required fields are not set.
         */
        fun build(): Whispers {
            require(::apiKey.isInitialized) {
                "API Key must be set. Use setApiKey() to configure it."
            }
            require(::publicKey.isInitialized) {
                "Public Key must be set. Use setPublicKey() to configure it."
            }
            return Whispers(this)
        }
    }
}
