package ai.whsprs.sdk.ui.components.messagelist

import ai.whsprs.common.ui.lazylist.LazyItemState
import androidx.compose.runtime.Stable
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.Companion.DefaultTransitionDuration
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.FadeIn
import ai.whsprs.sdk.ui.animations.LazyItemAnimationSpec.SlideIn
import java.time.OffsetDateTime

sealed interface ChatLazyItem : LazyItemState {

    val align: Align
    val date: OffsetDateTime
    val deliveryState: DeliveryState
    val position: Position
    val reactionsState: ReactionsState
    val animationSpec: LazyItemAnimationSpec
    val sizeSpec: SizeSpec
        get() = SizeSpec.Default

    @Stable
    data class Text(
        override val key: String,
        override val align: Align,
        override val date: OffsetDateTime,
        override val deliveryState: DeliveryState,
        override val position: Position,
        override val reactionsState: ReactionsState,
        override val animationSpec: LazyItemAnimationSpec,
        val text: String,
    ) : ChatLazyItem

    @Stable
    data class ConnectionError(
        override val date: OffsetDateTime,
        override val position: Position,
        override val animationSpec: LazyItemAnimationSpec,
        val text: String,
        val cta: String,
        val ctaMetadata: String,
    ) : ChatLazyItem {

        override val key: String = "__Synthetic_ConnectionError_Cell"
        override val align: Align = Align.Start
        override val reactionsState: ReactionsState = ReactionsState.Disabled
        override val deliveryState: DeliveryState = DeliveryState.Received()
    }

    @Stable
    data class SystemMessage(
        override val key: String,
        override val date: OffsetDateTime,
        val text: String
    ) : ChatLazyItem {

        override val align: Align = Align.Center
        override val deliveryState: DeliveryState = DeliveryState.Received()
        override val animationSpec: LazyItemAnimationSpec = LazyItemAnimationSpec.None
        override val position: Position = Position.Middle
        override val reactionsState: ReactionsState = ReactionsState.Disabled
    }

    @Stable
    data class DateSeparator(
        override val key: String,
        override val date: OffsetDateTime,
        val text: String
    ) : ChatLazyItem {

        override val align: Align = Align.Center
        override val deliveryState: DeliveryState = DeliveryState.Received()
        override val animationSpec: LazyItemAnimationSpec = LazyItemAnimationSpec.None
        override val position: Position = Position.Middle
        override val reactionsState: ReactionsState = ReactionsState.Disabled
    }

    @Stable
    data object TypingIndicator : ChatLazyItem {
        override val key: String = "TypingIndicator"
        override val align: Align = Align.Start
        override val date: OffsetDateTime = OffsetDateTime.now()
        override val deliveryState: DeliveryState = DeliveryState.Received()
        override val position: Position = Position.Top
        override val reactionsState: ReactionsState = ReactionsState.Disabled
        override val animationSpec: LazyItemAnimationSpec = SlideIn(repeatWhenAppeared = true) + FadeIn(DefaultTransitionDuration * 3, repeatWhenAppeared = true)
    }
}

enum class Position {
    Top,

    Middle,

    Bottom
}

enum class Align {

    Start,

    Center,

    End,
}

sealed interface ReactionsState {

    data object Disabled : ReactionsState

    data object NoReactions : ReactionsState

    data object Liked : ReactionsState

    data object Disliked : ReactionsState
}

sealed interface DeliveryState {

    data class Received(
        val waitingForMoreReplies: Boolean = false,
    ) : DeliveryState

    data object Pending : DeliveryState

    data object Sent : DeliveryState

    data object PollingFailed : DeliveryState

    data object Failed : DeliveryState
}

sealed interface SizeSpec {

    val factor: Float

    data object Fill : SizeSpec {
        override val factor: Float = 1f
    }

    data object Default : SizeSpec {
        @Deprecated("Will be ignored in the Message Cell v2 due to constant margin.")
        override val factor: Float = .8f
    }
}