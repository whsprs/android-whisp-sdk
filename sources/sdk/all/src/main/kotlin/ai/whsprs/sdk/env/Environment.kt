package ai.whsprs.sdk.env

interface Environment {

    val baseUrl: String
    val socketUrl: String
}

object WhispersEnvironment : Environment {

    override val socketUrl: String
        get() = System.getenv()["whisper_socket_url"].toString()

    override val baseUrl: String
        get() = System.getenv()["whisper_base_url"].toString()
}