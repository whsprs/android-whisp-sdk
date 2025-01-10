package ai.whsprs.sdk.ui.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class AnimatedContainerState {

    val animatedLazyListItems = mutableListOf<String>()
}

@Composable
fun rememberLazyListAnimationState(): AnimatedContainerState {
    return remember { AnimatedContainerState() }
}