package ai.whsprs.common.ui.style

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import ai.whsprs.common.ui_compose.R

@OptIn(ExperimentalTextApi::class)
val robotoFamily = FontFamily(
    Font(R.font.roboto_flex, weight = FontWeight.Light, variationSettings = FontVariation.Settings(FontWeight.Light, FontStyle.Normal)),
    Font(R.font.roboto_flex, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Normal)),
    Font(R.font.roboto_flex, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Italic)),
    Font(R.font.roboto_flex, weight = FontWeight.Medium, variationSettings = FontVariation.Settings(FontWeight.Medium, FontStyle.Normal)),
    Font(R.font.roboto_flex, weight = FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontWeight.SemiBold, FontStyle.Normal)),
    Font(R.font.roboto_flex, weight = FontWeight.Bold, variationSettings = FontVariation.Settings(FontWeight.Bold, FontStyle.Normal)),
    Font(R.font.roboto_flex, weight = FontWeight.ExtraBold, variationSettings = FontVariation.Settings(FontWeight.ExtraBold, FontStyle.Normal)),
)