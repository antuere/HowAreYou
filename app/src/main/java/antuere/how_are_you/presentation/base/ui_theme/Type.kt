package antuere.how_are_you.presentation.base.ui_theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R

//My fonts
val Roboto = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto, FontWeight.Normal),
)

val PlayfairDisplay = FontFamily(
    Font(R.font.playfair_display),
)

// Set of Material typography styles for Light mode
val TypographyLight = Typography(
    displayLarge = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.DisplayLargeWeight,
        fontSize = TypeTokens.DisplayLargeSize,
        lineHeight = TypeTokens.DisplayLargeLineHeight,
        letterSpacing = TypeTokens.DisplayLargeLetterSpacing
    ),
    displayMedium = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.DisplayMediumWeight,
        fontSize = TypeTokens.DisplayMediumSize,
        lineHeight = TypeTokens.DisplayMediumLineHeight,
        letterSpacing = TypeTokens.DisplayMediumLetterSpacing
    ),
    displaySmall = TextStyle(
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.DisplaySmallWeight,
        fontSize = TypeTokens.DisplaySmallSize,
        lineHeight = TypeTokens.DisplaySmallLineHeight,
        letterSpacing = TypeTokens.DisplaySmallLetterSpacing
    ),
    headlineLarge = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.HeadlineLargeWeight,
        fontSize = TypeTokens.HeadlineLargeSize,
        lineHeight = TypeTokens.HeadlineLargeLineHeight,
        letterSpacing = TypeTokens.HeadlineLargeLetterSpacing
    ),
    headlineMedium = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.HeadlineMediumWeight,
        fontSize = TypeTokens.HeadlineMediumSize,
        lineHeight = TypeTokens.HeadlineMediumLineHeight,
        letterSpacing = TypeTokens.HeadlineMediumLetterSpacing
    ),
    headlineSmall = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.HeadlineSmallWeight,
        fontSize = TypeTokens.HeadlineSmallSize,
        lineHeight = TypeTokens.HeadlineSmallLineHeight,
        letterSpacing = TypeTokens.HeadlineSmallLetterSpacing
    ),
    titleLarge = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.TitleLargeWeight,
        fontSize = TypeTokens.TitleLargeSize,
        lineHeight = TypeTokens.TitleLargeLineHeight,
        letterSpacing = TypeTokens.TitleLargeLetterSpacing
    ),
    titleMedium = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.TitleMediumWeight,
        fontSize = TypeTokens.TitleMediumSize,
        lineHeight = TypeTokens.TitleMediumLineHeight,
        letterSpacing = TypeTokens.TitleMediumLetterSpacing
    ),
    titleSmall = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.TitleSmallWeight,
        fontSize = TypeTokens.TitleSmallSize,
        lineHeight = TypeTokens.TitleSmallLineHeight,
        letterSpacing = TypeTokens.TitleSmallLetterSpacing
    ),
    labelLarge = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.LabelLargeWeight,
        fontSize = TypeTokens.LabelLargeSize,
        lineHeight = TypeTokens.LabelLargeLineHeight,
        letterSpacing = TypeTokens.LabelLargeLetterSpacing
    ),
    labelMedium = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.LabelMediumWeight,
        fontSize = TypeTokens.LabelMediumSize,
        lineHeight = TypeTokens.LabelMediumLineHeight,
        letterSpacing = TypeTokens.LabelMediumLetterSpacing
    ),
    labelSmall = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.LabelSmallWeight,
        fontSize = TypeTokens.LabelSmallSize,
        lineHeight = TypeTokens.LabelSmallLineHeight,
        letterSpacing = TypeTokens.LabelSmallLetterSpacing
    ),
    bodyLarge = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.BodyLargeWeight,
        fontSize = TypeTokens.BodyLargeSize,
        lineHeight = TypeTokens.BodyLargeLineHeight,
        letterSpacing = TypeTokens.BodyLargeLetterSpacing
    ),
    bodyMedium = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.BodyMediumWeight,
        fontSize = TypeTokens.BodyMediumSize,
        lineHeight = TypeTokens.BodyMediumLineHeight,
        letterSpacing = TypeTokens.BodyMediumLetterSpacing
    ),
    bodySmall = TextStyle(
        color = default_light_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.BodySmallWeight,
        fontSize = TypeTokens.BodySmallSize,
        lineHeight = TypeTokens.BodySmallLineHeight,
        letterSpacing = TypeTokens.BodySmallLetterSpacing
    ),
)

// Set of Material typography styles for Dark mode
val TypographyDark = Typography(
    displayLarge = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.DisplayLargeWeight,
        fontSize = TypeTokens.DisplayLargeSize,
        lineHeight = TypeTokens.DisplayLargeLineHeight,
        letterSpacing = TypeTokens.DisplayLargeLetterSpacing
    ),
    displayMedium = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.DisplayMediumWeight,
        fontSize = TypeTokens.DisplayMediumSize,
        lineHeight = TypeTokens.DisplayMediumLineHeight,
        letterSpacing = TypeTokens.DisplayMediumLetterSpacing
    ),
    displaySmall = TextStyle(
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.DisplaySmallWeight,
        fontSize = TypeTokens.DisplaySmallSize,
        lineHeight = TypeTokens.DisplaySmallLineHeight,
        letterSpacing = TypeTokens.DisplaySmallLetterSpacing
    ),
    headlineLarge = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.HeadlineLargeWeight,
        fontSize = TypeTokens.HeadlineLargeSize,
        lineHeight = TypeTokens.HeadlineLargeLineHeight,
        letterSpacing = TypeTokens.HeadlineLargeLetterSpacing
    ),
    headlineMedium = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.HeadlineMediumWeight,
        fontSize = TypeTokens.HeadlineMediumSize,
        lineHeight = TypeTokens.HeadlineMediumLineHeight,
        letterSpacing = TypeTokens.HeadlineMediumLetterSpacing
    ),
    headlineSmall = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.HeadlineSmallWeight,
        fontSize = TypeTokens.HeadlineSmallSize,
        lineHeight = TypeTokens.HeadlineSmallLineHeight,
        letterSpacing = TypeTokens.HeadlineSmallLetterSpacing
    ),
    titleLarge = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.TitleLargeWeight,
        fontSize = TypeTokens.TitleLargeSize,
        lineHeight = TypeTokens.TitleLargeLineHeight,
        letterSpacing = TypeTokens.TitleLargeLetterSpacing
    ),
    titleMedium = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.TitleMediumWeight,
        fontSize = TypeTokens.TitleMediumSize,
        lineHeight = TypeTokens.TitleMediumLineHeight,
        letterSpacing = TypeTokens.TitleMediumLetterSpacing
    ),
    titleSmall = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.TitleSmallWeight,
        fontSize = TypeTokens.TitleSmallSize,
        lineHeight = TypeTokens.TitleSmallLineHeight,
        letterSpacing = TypeTokens.TitleSmallLetterSpacing
    ),
    labelLarge = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.LabelLargeWeight,
        fontSize = TypeTokens.LabelLargeSize,
        lineHeight = TypeTokens.LabelLargeLineHeight,
        letterSpacing = TypeTokens.LabelLargeLetterSpacing
    ),
    labelMedium = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.LabelMediumWeight,
        fontSize = TypeTokens.LabelMediumSize,
        lineHeight = TypeTokens.LabelMediumLineHeight,
        letterSpacing = TypeTokens.LabelMediumLetterSpacing
    ),
    labelSmall = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.LabelSmallWeight,
        fontSize = TypeTokens.LabelSmallSize,
        lineHeight = TypeTokens.LabelSmallLineHeight,
        letterSpacing = TypeTokens.LabelSmallLetterSpacing
    ),
    bodyLarge = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.BodyLargeWeight,
        fontSize = TypeTokens.BodyLargeSize,
        lineHeight = TypeTokens.BodyLargeLineHeight,
        letterSpacing = TypeTokens.BodyLargeLetterSpacing
    ),
    bodyMedium = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.BodyMediumWeight,
        fontSize = TypeTokens.BodyMediumSize,
        lineHeight = TypeTokens.BodyMediumLineHeight,
        letterSpacing = TypeTokens.BodyMediumLetterSpacing
    ),
    bodySmall = TextStyle(
        color = default_dark_theme_onBackground,
        fontFamily = TypeTokens.DefaultFont,
        fontWeight = TypeTokens.BodySmallWeight,
        fontSize = TypeTokens.BodySmallSize,
        lineHeight = TypeTokens.BodySmallLineHeight,
        letterSpacing = TypeTokens.BodySmallLetterSpacing
    ),
)

val MyShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(48.dp)
)