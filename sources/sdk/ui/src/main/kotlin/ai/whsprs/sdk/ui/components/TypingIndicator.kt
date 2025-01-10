package ai.whsprs.sdk.ui.components

import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
internal fun TypingIndicator(
    modifier: Modifier = Modifier,
    dotsCount: Int = 3,
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        (0..<dotsCount).forEach { index ->
            TypingDot(
                alpha = calculateDotAlpha(
                    progress = progress.value,
                    totalDots = dotsCount,
                    dotIndex = index
                )
            )
        }
    }
}

@Composable
private fun TypingDot(alpha: Float) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .background(
                color = WhisperTheme.Colors.Text.Primary.copy(alpha = alpha),
                shape = CircleShape
            )
    )
}

@Stable
private fun calculateDotAlpha(
    progress: Float,
    totalDots: Int,
    dotIndex: Int
): Float {
    val minAlpha = 0.1f
    val maxAlpha = 1.0f
    val alphaRange = maxAlpha - minAlpha
    val segmentLength = 1.0f / totalDots
    val startProgress = dotIndex * segmentLength
    val endProgress = (dotIndex + 1) * segmentLength

    return when {
        progress < startProgress -> minAlpha
        progress < endProgress -> minAlpha + alphaRange * ((progress - startProgress) / segmentLength)
        else -> maxAlpha
    }
}

@Composable
@Preview
private fun TypingIndicatorPreview() {
    TypingIndicator()
}
