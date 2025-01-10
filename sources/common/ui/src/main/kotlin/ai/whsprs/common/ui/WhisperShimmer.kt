package ai.whsprs.common.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun rememberWhisperShimmer() = rememberShimmer(
    shimmerBounds = ShimmerBounds.View,
    theme = LocalShimmerTheme.current.copy(
        shaderColors = listOf(
            Color.White.copy(alpha = 0.5f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.5f),
        ),
        animationSpec = infiniteRepeatable(
            animation = tween(
                2000,
                easing = LinearEasing,
                delayMillis = 0,
            ),
            repeatMode = RepeatMode.Restart,
        )
    )
)