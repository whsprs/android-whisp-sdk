package ai.whsprs.environment

class Signing(
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String
) {

    companion object {
        val debug: Signing
            get() = Signing(
                storePassword = "android",
                keyAlias = "androiddebugkey",
                keyPassword = "android"
            )

        val release: Signing
            get() = Signing(
                storePassword = System.getenv()["STORE_PASSWORD"].orEmpty(),
                keyAlias = System.getenv()["KEY_ALIAS"].orEmpty(),
                keyPassword = System.getenv()["KEY_PASSWORD"].orEmpty()
            )
    }
}