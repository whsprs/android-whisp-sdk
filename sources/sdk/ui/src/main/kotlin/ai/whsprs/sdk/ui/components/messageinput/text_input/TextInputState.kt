package ai.whsprs.sdk.ui.components.messageinput.text_input

data class TextInputState(
    val input: String,
    val hint: String,
    val isEnabled: Boolean,
) {

    companion object {

        val Default = TextInputState(
            input = "",
            hint = "Write your message...",
            isEnabled = true,
        )

        val EmptyInputPreview = Default.copy(input = "")

        val FilledInputPreview = Default.copy(input = "Some input")
    }
}