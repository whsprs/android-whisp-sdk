package ai.whsprs.sdk.be.chat.api

import ai.whsprs.sdk.be.chat.api.model.agent.AgentInfoResponse
import ai.whsprs.sdk.be.chat.api.model.connection.WhispInitializeRequestBody
import ai.whsprs.sdk.be.chat.api.model.message.CreateMessage
import ai.whsprs.sdk.be.chat.api.model.message.CreatedMessage
import ai.whsprs.sdk.be.chat.api.model.message.WhisperMessages
import ai.whsprs.sdk.be.chat.api.model.polling.PollingReplies
import ai.whsprs.sdk.be.chat.api.model.session.CreateSession
import ai.whsprs.sdk.be.chat.api.model.session.WhisperSession
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WhisperChatApi {

    @POST("initialize_connection")
    suspend fun initialize(@Body parameters: WhispInitializeRequestBody)

    @GET("connect")
    suspend fun connect(@Header("X-API-Key") apiKey: String)

    @GET("agent_info")
    suspend fun getAgentInfo(): AgentInfoResponse

    @POST("$Slug/session")
    suspend fun createSession(@Body body: CreateSession): WhisperSession

    @POST("$Slug/message")
    suspend fun createMessage(@Body body: CreateMessage): CreatedMessage

    @Deprecated("Move to ws implementation.")
    @GET("$Slug/messages")
    suspend fun getMessages(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): WhisperMessages

    @Deprecated("Move to ws implementation.")
    @GET("$Slug/messages/poll/{messageId}")
    suspend fun getReplies(@Path("messageId") messageId: String): PollingReplies

    @POST("$Slug/message/{messageId}/like")
    suspend fun likeMessage(@Path("messageId") messageId: String)

    @POST("$Slug/message/{messageId}/dislike")
    suspend fun dislikeMessage(@Path("messageId") messageId: String)

    private companion object {

        private const val Slug = "v0/chat"
    }
}