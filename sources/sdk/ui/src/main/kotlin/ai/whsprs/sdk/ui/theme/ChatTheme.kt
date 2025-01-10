package ai.whsprs.sdk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

private val LocalShapes = compositionLocalOf<ChatShapes> {
    error("No shapes provided.")
}
private val LocalColors = compositionLocalOf<ChatColors> {
    error("No colors provided.")
}
private val LocalDimens = compositionLocalOf<ChatDimens> {
    error("No dimens provided.")
}

@Composable
fun ChatTheme(
    shapes: ChatShapes = ChatShapes.defaultShapes(),
    colors: ChatColors = ChatColors.defaultColors(),
    dimens: ChatDimens = ChatDimens.defaultDimens(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalShapes provides shapes,
        LocalColors provides colors,
        LocalDimens provides dimens,
    ) {
        content()
    }
}

object ChatTheme {

    val shapes: ChatShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val colors: ChatColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val dimens: ChatDimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}