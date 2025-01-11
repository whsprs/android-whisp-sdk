package ai.whsprs.sdk.ui.components.topappbar

import ai.whsprs.common.ui.WhisperPreview
import ai.whsprs.common.ui.components.appbar.TopAppBar
import ai.whsprs.common.ui.rememberWhisperShimmer
import ai.whsprs.common.ui.style.Body14Regular
import ai.whsprs.common.ui.style.Heading4
import ai.whsprs.common.ui.style.WhisperTheme
import ai.whsprs.sdk.ui.R
import ai.whsprs.sdk.ui.components.whispericon.WhisperIcon
import ai.whsprs.sdk.ui.theme.ChatTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ChatTopAppBar(
    state: ChatTopAppBarState,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        leadingContent = {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(56.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ds_arrow_left),
                    contentDescription = null,
                    tint = WhisperTheme.Colors.Text.Primary
                )
            }
        },
        titleContent = {
            if (state.isLoading) {
                Row(
                    modifier = Modifier.shimmer(rememberWhisperShimmer()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WhisperIcon(modifier = Modifier.size(40.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .width(102.dp)
                                .height(18.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(WhisperTheme.Colors.Fill.Tertiary)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(14.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(WhisperTheme.Colors.Fill.Tertiary)
                        )
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WhisperIcon(modifier = Modifier.size(40.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = state.title,
                            style = Heading4,
                            color = WhisperTheme.Colors.Text.Primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = state.subtitle,
                            style = Body14Regular,
                            color = WhisperTheme.Colors.Core.Accent,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        },
    )
}

@Composable
@WhisperPreview
private fun ChatTopAppBarPreview() {
    ChatTheme {
        ChatTopAppBar(
            state = ChatTopAppBarState(
                title = "whisp",
                subtitle = "online"
            )
        )
    }
}
