package ai.whsprs.sdk.ui.components.topappbar


data class ChatTopAppBarState(
    val title: String,
    val subtitle: String,
) {

    val isLoading: Boolean
        get() = title.isEmpty() || subtitle.isEmpty()

    companion object {

        val Default = ChatTopAppBarState(
            title = "",
            subtitle = "",
        )
    }
}
