package ai.whsprs.sdk.ui.animations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.TransformOrigin
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.FadeIn
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.None
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.ScaleUp
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.SlideIn
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.SlideIn.Direction.HorizontalLtr
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.SlideIn.Direction.HorizontalRtl
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.SlideIn.Direction.Vertical

/**
 * Describes the appearance behaviour of the Chat items
 */
sealed interface LazyItemAnimationSpec {

    val duration: Int
        get() = DefaultTransitionDuration

    val repeatWhenAppeared: Boolean

    data object None : LazyItemAnimationSpec {
        override val duration: Int = 0
        override val repeatWhenAppeared: Boolean = false
    }

    data class ScaleUp(
        override val duration: Int = DefaultTransitionDuration,
        override val repeatWhenAppeared: Boolean = false,
        val initialScale: Float = 0f,
        val transformOrigin: TransformOrigin = TransformOrigin.Center,
    ) : LazyItemAnimationSpec

    data class SlideIn(
        override val duration: Int = DefaultTransitionDuration,
        override val repeatWhenAppeared: Boolean = false,
        val direction: Direction = Vertical,
        val initialOffsetY: (Float) -> Float = { it },
        val initialOffsetX: (Float) -> Float = {
            when (direction) {
                HorizontalLtr -> -it
                else -> it
            }
        },
    ) : LazyItemAnimationSpec {

        enum class Direction { Vertical, HorizontalRtl, HorizontalLtr }
    }

    data class FadeIn(
        override val duration: Int = DefaultTransitionDuration,
        override val repeatWhenAppeared: Boolean = false
    ) : LazyItemAnimationSpec

    data class Composite(
        val specs: List<LazyItemAnimationSpec>,
        override val repeatWhenAppeared: Boolean = specs.any(LazyItemAnimationSpec::repeatWhenAppeared)
    ) : LazyItemAnimationSpec

    operator fun plus(other: LazyItemAnimationSpec): LazyItemAnimationSpec =
        when (this) {
            is Composite -> Composite(specs + other)
            else -> Composite(listOf(this, other))
        }

    companion object {
        const val DefaultTransitionDuration = 400

        val SingleMessageAppearanceAnimationSpec =
            ScaleUp() + SlideIn() + FadeIn(DefaultTransitionDuration * 3)
    }
}

@Stable
internal fun LazyItemAnimationSpec.buildTransition(): EnterTransition {
    return when (this) {
        is None -> EnterTransition.None
        is ScaleUp -> scaleIn(
            animationSpec = tween(duration),
            initialScale = initialScale,
            transformOrigin = transformOrigin
        )
        is FadeIn -> fadeIn(animationSpec = tween(duration))
        is SlideIn -> {
            when (direction) {
                Vertical -> slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(duration)
                )

                HorizontalRtl -> slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(duration)
                )

                HorizontalLtr -> slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(duration)
                )
            }
        }

        is LazyItemAnimationSpec.Composite -> specs
            .fold(EnterTransition.None) { transition, spec -> transition + spec.buildTransition() }
    }
}