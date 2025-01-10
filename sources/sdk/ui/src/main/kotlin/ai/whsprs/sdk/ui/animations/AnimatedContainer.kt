package ai.whsprs.sdk.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.None

@Composable
internal fun AnimatedContainer(
    animationSpec: LazyItemAnimationSpec,
    animatedContainerState: AnimatedContainerState,
    modifier: Modifier = Modifier,
    label: String = "AnimatedContainer",
    content: @Composable () -> Unit
) {

    val isAnimated = animatedContainerState.animatedLazyListItems.contains(label)
            && !animationSpec.repeatWhenAppeared

    val state = remember(label) {
        MutableTransitionState(initialState = isAnimated).apply {
            animatedContainerState.animatedLazyListItems.add(label)
            targetState = true
        }
    }

    when {
        isAnimated || animationSpec is None -> content()
        else -> AnimatedVisibility(
            visibleState = state,
            modifier = modifier,
            enter = animationSpec.buildTransition(),
            label = label,
            content = { content() }
        )
    }
}