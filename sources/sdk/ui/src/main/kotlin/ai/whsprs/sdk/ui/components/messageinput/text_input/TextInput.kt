package ai.whsprs.sdk.ui.components.messageinput.text_input

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun TextInput(
    state: TextInputState,
    modifier: Modifier = Modifier,
    onInputChange: (String) -> Unit = {},
    onSendClick: () -> Unit = {},
) {
    BasicTextField(
        modifier = modifier
            .defaultMinSize(minHeight = 32.dp)
            .verticalScroll(rememberScrollState()),
        value = state.input,
        onValueChange = onInputChange,
        minLines = 1,
        maxLines = 4,
        enabled = state.isEnabled,
        textStyle = TextStyle(
            fontSize = 16.sp,
            color = WhisperTheme.Colors.Text.Primary,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Send,
            capitalization = KeyboardCapitalization.Sentences,
        ),
        keyboardActions = KeyboardActions(
            onSend = { onSendClick() }
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = WhisperTheme.Colors.Fill.Tertiary,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (state.input.isEmpty()) {
                    Text(
                        text = state.hint,
                        fontSize = 16.sp,
                        color = WhisperTheme.Colors.Text.Secondary
                    )
                }
                innerTextField()
            }
        },
        cursorBrush = SolidColor(WhisperTheme.Colors.Text.Accent),
    )
}

@WhisperPreview
@Composable
private fun InputPreview() {
    TextInput(state = TextInputState.EmptyInputPreview)
}