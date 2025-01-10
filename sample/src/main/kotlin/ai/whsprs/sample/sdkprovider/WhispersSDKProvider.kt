package ai.whsprs.sample.sdkprovider

import ai.whsprs.sdk.Environment
import ai.whsprs.sdk.LogLevel
import ai.whsprs.sdk.Whispers
import android.content.Context

interface WhispersSDKProvider {

    suspend fun provide(): Whispers
}

private class WhispersSDKProviderImpl(
    private val apiKey: String,
    private val publicKey: String,
    private val environment: Environment,
) : WhispersSDKProvider {

    private var whispers: Whispers? = null

    override suspend fun provide(): Whispers {
        return whispers ?: Whispers.Builder()
            .setApiKey(apiKey)
            .setPublicKey(publicKey)
            .setEnvironment(environment)
            .setLogLevel(LogLevel.ERROR)
            .build()
            .also { whispers = it }
    }
}