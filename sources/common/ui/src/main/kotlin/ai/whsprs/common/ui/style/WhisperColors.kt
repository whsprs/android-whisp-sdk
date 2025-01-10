@file:Suppress("PropertyName")

package ai.whsprs.common.ui.style

import android.util.Log
import androidx.compose.ui.graphics.Color

interface WhisperColors {

    interface CoreColors {
        val PrimaryA: Color
        val PrimaryB: Color
        val Accent: Color
    }

    interface ExtensionColors {
        val Warning: Color
        val Caution: Color
        val Alert: Color
        val Success: Color
        val Confirmation: Color
    }

    interface TextColors {
        val Primary: Color
        val PrimaryInverse: Color
        val PrimaryWhite: Color
        val PrimaryBlack: Color
        val Secondary: Color
        val SecondaryWhite: Color
        val Tertiary: Color
        val TertiaryWhite: Color
        val Quaternary: Color
        val Accent: Color
        val HeadingBlue: Color
    }

    interface BackgroundColors {
        val Foundation: Color
        val Base: Color
        val Primary: Color
        val Secondary: Color
        val Tertiary: Color
        val SystemOverlay: Color
        val WhisperPrimary: Color
        val WhisperSecondary: Color
        val WhisperBase: Color
    }

    interface FillColors {
        val Accent: Color
        val Secondary: Color
        val Tertiary: Color
        val AccentAlternate: Color
        val SuccessAlternate: Color
        val CautionAlternate: Color
        val WarningAlternate: Color
        val WhisperAlternate: Color
    }

    interface FeatureColors {
        val Whisper: Color
    }

    interface BorderColors {
        val Primary: Color
        val PrimaryWhite: Color
        val Secondary: Color
        val SecondaryWhite: Color
        val Accent: Color
    }

    interface HelperColors {
        val SystemBlack: Color
    }

    val Core: CoreColors
    val Extension: ExtensionColors
    val Text: TextColors
    val Background: BackgroundColors
    val Fill: FillColors
    val Feature: FeatureColors
    val Border: BorderColors
    val Helper: HelperColors
}

object WhisperColorsLight : WhisperColors {
    override val Core = object : WhisperColors.CoreColors {
        override val PrimaryA = Color(0xFF323653)
        override val PrimaryB = Color(0xFFFFFFFF)
        override val Accent = Color(0xFF7D8BF7)
    }
    override val Extension = object : WhisperColors.ExtensionColors {
        override val Warning = Color(0xFFEA5771)
        override val Caution = Color(0xFFF3AA51)
        override val Alert = Color(0xFFF2C94C)
        override val Success = Color(0xFF7BC947)
        override val Confirmation = Color(0xFF54B674)
    }
    override val Text = object : WhisperColors.TextColors {
        override val Primary = Color(0xFF323653)
        override val PrimaryInverse = Color(0xFFFFFFFF)
        override val PrimaryWhite = Color(0xFFFFFFFF)
        override val PrimaryBlack = Color(0xFF323653)
        override val Secondary = Color(0x99323653)
        override val SecondaryWhite = Color(0x99FFFFFF)
        override val Tertiary = Color(0x66323653)
        override val TertiaryWhite = Color(0x66FFFFFF)
        override val Quaternary = Color(0x40323653)
        override val Accent = Color(0xFF7D8BF7)
        override val HeadingBlue = Color(0xFF424982)
    }
    override val Background = object : WhisperColors.BackgroundColors {
        override val Foundation = Color(0xFF34365E)
        override val Base = Color(0xFFF5F6F8)
        override val Primary = Color(0xFFFFFFFF)
        override val Secondary = Color(0xFFF5F6F8)
        override val Tertiary = Color(0xFFFFFFFF)
        override val SystemOverlay = Color(0xB3000000)
        override val WhisperPrimary: Color = Color(0xFFF5F6F8)
        override val WhisperSecondary: Color = Color(0xFFFFFFFF)
        override val WhisperBase: Color = Color(0xFFFFFFFF)

    }
    override val Fill = object : WhisperColors.FillColors {
        override val Accent = Color(0xFF7D8BF7)
        override val Secondary = Color(0x33323653)
        override val Tertiary = Color(0x14323653)
        override val AccentAlternate = Color(0x2B7D8BF7)
        override val SuccessAlternate = Color(0x337BC947)
        override val CautionAlternate = Color(0x33F3AA51)
        override val WarningAlternate = Color(0x2BEA5771)
        override val WhisperAlternate = Color(0x1A8952FF)
    }
    override val Border = object : WhisperColors.BorderColors {
        override val Primary = Color(0x4D323653)
        override val PrimaryWhite = Color(0x4DFFFFFF)
        override val Secondary = Color(0x1A323653)
        override val SecondaryWhite = Color(0x1AFFFFFF)
        override val Accent = Color(0xFF7D8BF7)
    }
    override val Feature = object : WhisperColors.FeatureColors {
        override val Whisper = Color(0xFF9669F7)
    }
    override val Helper = object : WhisperColors.HelperColors {
        override val SystemBlack = Color(0xFF000000)
    }
}

object WhisperColorsDark : WhisperColors {
    override val Core = object : WhisperColors.CoreColors {
        override val PrimaryA = Color(0xFFFFFFFF)
        override val PrimaryB = Color(0xFF252736)
        override val Accent = Color(0xFF98A4FF)
    }
    override val Extension = object : WhisperColors.ExtensionColors {
        override val Warning = Color(0xFFFD687E)
        override val Caution = Color(0xFFFFBA66)
        override val Alert = Color(0xFFF0BE26)
        override val Success = Color(0xFF8CDA58)
        override val Confirmation = Color(0xFF64C986)
    }
    override val Text = object : WhisperColors.TextColors {
        override val Primary = Color(0xFFFFFFFF)
        override val PrimaryInverse = Color(0xFF323653)
        override val PrimaryWhite = Color(0xFFFFFFFF)
        override val PrimaryBlack = Color(0xFF323653)
        override val Secondary = Color(0x99FFFFFF)
        override val SecondaryWhite = Color(0x99FFFFFF)
        override val Tertiary = Color(0x66FFFFFF)
        override val TertiaryWhite = Color(0x66FFFFFF)
        override val Quaternary = Color(0x40FFFFFF)
        override val Accent = Color(0xFF98A4FF)
        override val HeadingBlue = Color(0xFFFFFFFF)
    }
    override val Background = object : WhisperColors.BackgroundColors {
        override val Foundation = Color(0xFF14151D)
        override val Base = Color(0xFF181A25)
        override val Primary = Color(0xFF252736)
        override val Secondary = Color(0xFF181A25)
        override val Tertiary = Color(0xFF373A4E)
        override val SystemOverlay = Color(0xB3000000)
        override val WhisperPrimary: Color = Color(0xFF252736)
        override val WhisperSecondary: Color = Color(0xFF181A25)
        override val WhisperBase: Color = Color(0xFF181A25)
    }
    override val Fill = object : WhisperColors.FillColors {
        override val Accent = Color(0xFF98A4FF)
        override val Secondary = Color(0x66FFFFFF)
        override val Tertiary = Color(0x14FFFFFF)
        override val AccentAlternate = Color(0x2B98A4FF)
        override val SuccessAlternate = Color(0x268CDA58)
        override val CautionAlternate = Color(0x29FFA435)
        override val WarningAlternate = Color(0x2BFD687E)
        override val WhisperAlternate = Color(0x269669F7)
    }
    override val Border = object : WhisperColors.BorderColors {
        override val Primary = Color(0x80FFFFFF)
        override val PrimaryWhite = Color(0x80FFFFFF)
        override val Secondary = Color(0x1AFFFFFF)
        override val SecondaryWhite = Color(0x1AFFFFFF)
        override val Accent = Color(0xFF98A4FF)
    }
    override val Feature = object : WhisperColors.FeatureColors {
        override val Whisper = Color(0xFF9669F7)
    }
    override val Helper = object : WhisperColors.HelperColors {
        override val SystemBlack = Color(0xFF000000)
    }
}

fun String.toComposeColor() = try {
    Color(android.graphics.Color.parseColor(this))
} catch (ex: Exception) {
    Color.Unspecified
}