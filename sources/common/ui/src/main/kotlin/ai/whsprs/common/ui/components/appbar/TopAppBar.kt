package ai.whsprs.common.ui.components.appbar

import ai.whsprs.common.ui.style.WhisperTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBar(
   modifier: Modifier = Modifier,
   leadingContent: @Composable () -> Unit = {},
   titleContent: @Composable () -> Unit = {},
   actionsContent: @Composable () -> Unit = {},
   shape: Shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
   elevation: Dp = 4.dp,
) {
   Surface(
      modifier = modifier,
      shape = shape,
      shadowElevation = elevation,
      color = WhisperTheme.Colors.Background.Primary,
      contentColor = WhisperTheme.Colors.Background.Primary,
   ) {
      Row(
         verticalAlignment = Alignment.CenterVertically,
         modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(72.dp)
      ) {
         leadingContent()
         titleContent()
         Spacer(modifier = Modifier.weight(1f))
         actionsContent()
      }
   }
}