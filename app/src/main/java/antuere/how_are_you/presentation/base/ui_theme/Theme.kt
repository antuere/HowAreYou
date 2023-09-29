package antuere.how_are_you.presentation.base.ui_theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import antuere.domain.dto.AppTheme
import antuere.how_are_you.LocalDarkThemeValue

private val DefaultColorScheme_Dark = darkColorScheme(
    primary = default_dark_theme_primary,
    onPrimary = default_dark_theme_onPrimary,
    primaryContainer = default_dark_theme_primaryContainer,
    onPrimaryContainer = default_dark_theme_onPrimaryContainer,
    secondary = default_dark_theme_secondary,
    onSecondary = default_dark_theme_onSecondary,
    secondaryContainer = default_dark_theme_secondaryContainer,
    onSecondaryContainer = default_dark_theme_onSecondaryContainer,
    tertiary = default_dark_theme_tertiary,
    surface = default_dark_theme_surface,
    surfaceVariant = default_dark_theme_surfaceVariant,
    onSurface = default_dark_theme_onSurface,
    onSurfaceVariant = default_dark_theme_onSurfaceVariant,
    background = default_dark_theme_background,
    onBackground = default_dark_theme_onBackground,
    outline = default_dark_theme_outline,
    error = default_dark_theme_error,
    outlineVariant = default_dark_theme_outline_variant,
    inverseSurface = default_dark_theme_inverseSurface,
)

private val DefaultColorScheme_Light = lightColorScheme(
    primary = default_light_theme_primary,
    onPrimary = default_light_theme_onPrimary,
    primaryContainer = default_light_theme_primaryContainer,
    onPrimaryContainer = default_light_theme_onPrimaryContainer,
    secondary = default_light_theme_secondary,
    onSecondary = default_light_theme_onSecondary,
    secondaryContainer = default_light_theme_secondaryContainer,
    onSecondaryContainer = default_light_theme_onSecondaryContainer,
    tertiary = default_light_theme_tertiary,
    surface = default_light_theme_surface,
    surfaceVariant = default_light_theme_surfaceVariant,
    onSurface = default_light_theme_onSurface,
    onSurfaceVariant = default_light_theme_onSurfaceVariant,
    background = default_light_theme_background,
    onBackground = default_light_theme_onBackground,
    outline = default_light_theme_outline,
    error = default_light_theme_error,
    outlineVariant = default_light_theme_outline_variant,
    inverseSurface = default_light_theme_inverseSurface,

    )

private val RedColorScheme_Dark = darkColorScheme(
    primary = red_dark_theme_primary,
    onPrimary = red_dark_theme_onPrimary,
    primaryContainer = red_dark_theme_primaryContainer,
    onPrimaryContainer = red_dark_theme_onPrimaryContainer,
    secondary = red_dark_theme_secondary,
    onSecondary = red_dark_theme_onSecondary,
    secondaryContainer = red_dark_theme_secondaryContainer,
    onSecondaryContainer = red_dark_theme_onSecondaryContainer,
    tertiary = red_dark_theme_tertiary,
    surface = red_dark_theme_surface,
    surfaceVariant = red_dark_theme_surfaceVariant,
    onSurface = red_dark_theme_onSurface,
    onSurfaceVariant = red_dark_theme_onSurfaceVariant,
    background = red_dark_theme_background,
    onBackground = red_dark_theme_onBackground,
    outline = red_dark_theme_outline,
    error = red_dark_theme_error,
    outlineVariant = red_dark_theme_outline_variant,
    inverseSurface = red_dark_theme_inverseSurface,
)

private val RedColorScheme_Light = lightColorScheme(
    primary = red_light_theme_primary,
    onPrimary = red_light_theme_onPrimary,
    primaryContainer = red_light_theme_primaryContainer,
    onPrimaryContainer = red_light_theme_onPrimaryContainer,
    secondary = red_light_theme_secondary,
    onSecondary = red_light_theme_onSecondary,
    secondaryContainer = red_light_theme_secondaryContainer,
    onSecondaryContainer = red_light_theme_onSecondaryContainer,
    tertiary = red_light_theme_tertiary,
    surface = red_light_theme_surface,
    surfaceVariant = red_light_theme_surfaceVariant,
    onSurface = red_light_theme_onSurface,
    onSurfaceVariant = red_light_theme_onSurfaceVariant,
    background = red_light_theme_background,
    onBackground = red_light_theme_onBackground,
    outline = red_light_theme_outline,
    error = red_light_theme_error,
    outlineVariant = red_light_theme_outline_variant,
    inverseSurface = red_light_theme_inverseSurface,
)

private val GreenColorScheme_Dark = darkColorScheme(
    primary = green_dark_theme_primary,
    onPrimary = green_dark_theme_onPrimary,
    primaryContainer = green_dark_theme_primaryContainer,
    onPrimaryContainer = green_dark_theme_onPrimaryContainer,
    secondary = green_dark_theme_secondary,
    onSecondary = green_dark_theme_onSecondary,
    secondaryContainer = green_dark_theme_secondaryContainer,
    onSecondaryContainer = green_dark_theme_onSecondaryContainer,
    tertiary = green_dark_theme_tertiary,
    surface = green_dark_theme_surface,
    surfaceVariant = green_dark_theme_surfaceVariant,
    onSurface = green_dark_theme_onSurface,
    onSurfaceVariant = green_dark_theme_onSurfaceVariant,
    background = green_dark_theme_background,
    onBackground = green_dark_theme_onBackground,
    outline = green_dark_theme_outline,
    error = green_dark_theme_error,
    outlineVariant = green_dark_theme_outline_variant,
    inverseSurface = green_dark_theme_inverseSurface,
)

private val GreenColorScheme_Light = lightColorScheme(
    primary = green_light_theme_primary,
    onPrimary = green_light_theme_onPrimary,
    primaryContainer = green_light_theme_primaryContainer,
    onPrimaryContainer = green_light_theme_onPrimaryContainer,
    secondary = green_light_theme_secondary,
    onSecondary = green_light_theme_onSecondary,
    secondaryContainer = green_light_theme_secondaryContainer,
    onSecondaryContainer = green_light_theme_onSecondaryContainer,
    tertiary = green_light_theme_tertiary,
    surface = green_light_theme_surface,
    surfaceVariant = green_light_theme_surfaceVariant,
    onSurface = green_light_theme_onSurface,
    onSurfaceVariant = green_light_theme_onSurfaceVariant,
    background = green_light_theme_background,
    onBackground = green_light_theme_onBackground,
    outline = green_light_theme_outline,
    error = green_light_theme_error,
    outlineVariant = green_light_theme_outline_variant,
    inverseSurface = green_light_theme_inverseSurface,
)

private val YellowColorScheme_Dark = darkColorScheme(
    primary = yellow_dark_theme_primary,
    onPrimary = yellow_dark_theme_onPrimary,
    primaryContainer = yellow_dark_theme_primaryContainer,
    onPrimaryContainer = yellow_dark_theme_onPrimaryContainer,
    secondary = yellow_dark_theme_secondary,
    onSecondary = yellow_dark_theme_onSecondary,
    secondaryContainer = yellow_dark_theme_secondaryContainer,
    onSecondaryContainer = yellow_dark_theme_onSecondaryContainer,
    tertiary = yellow_dark_theme_tertiary,
    surface = yellow_dark_theme_surface,
    surfaceVariant = yellow_dark_theme_surfaceVariant,
    onSurface = yellow_dark_theme_onSurface,
    onSurfaceVariant = yellow_dark_theme_onSurfaceVariant,
    background = yellow_dark_theme_background,
    onBackground = yellow_dark_theme_onBackground,
    outline = yellow_dark_theme_outline,
    error = yellow_dark_theme_error,
    outlineVariant = yellow_dark_theme_outline_variant,
    inverseSurface = yellow_dark_theme_inverseSurface,
)

private val YellowColorScheme_Light = lightColorScheme(
    primary = yellow_light_theme_primary,
    onPrimary = yellow_light_theme_onPrimary,
    primaryContainer = yellow_light_theme_primaryContainer,
    onPrimaryContainer = yellow_light_theme_onPrimaryContainer,
    secondary = yellow_light_theme_secondary,
    onSecondary = yellow_light_theme_onSecondary,
    secondaryContainer = yellow_light_theme_secondaryContainer,
    onSecondaryContainer = yellow_light_theme_onSecondaryContainer,
    tertiary = yellow_light_theme_tertiary,
    surface = yellow_light_theme_surface,
    surfaceVariant = yellow_light_theme_surfaceVariant,
    onSurface = yellow_light_theme_onSurface,
    onSurfaceVariant = yellow_light_theme_onSurfaceVariant,
    background = yellow_light_theme_background,
    onBackground = yellow_light_theme_onBackground,
    outline = yellow_light_theme_outline,
    error = yellow_light_theme_error,
    outlineVariant = yellow_light_theme_outline_variant,
    inverseSurface = yellow_light_theme_inverseSurface,
)

@Composable
fun HowAreYouTheme(
    isDarkTheme: Boolean = LocalDarkThemeValue.current,
    appTheme: AppTheme,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (appTheme) {
        AppTheme.DEFAULT -> if (isDarkTheme) DefaultColorScheme_Dark else DefaultColorScheme_Light
        AppTheme.RED -> if (isDarkTheme) RedColorScheme_Dark else RedColorScheme_Light
//        AppTheme.GREEN -> if (isDarkTheme) GreenColorScheme_Dark else GreenColorScheme_Light
//        AppTheme.YELLOW -> if (isDarkTheme) YellowColorScheme_Dark else YellowColorScheme_Light
        else -> if (isDarkTheme) DefaultColorScheme_Dark else DefaultColorScheme_Light
    }

    val typography = when (isDarkTheme) {
        true -> TypographyDark
        false -> TypographyLight
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content,
        shapes = MyShapes
    )
}