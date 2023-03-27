package antuere.how_are_you.presentation.base.ui_theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = theme_primary,
    onPrimary = theme_onPrimary,
    primaryContainer = theme_primaryContainer,
    onPrimaryContainer = theme_onPrimaryContainer,
    secondary = theme_secondary,
    onSecondary = theme_onSecondary,
    secondaryContainer = theme_secondaryContainer,
    onSecondaryContainer = theme_onSecondaryContainer,
    tertiary = theme_tertiary,
    surface = theme_surface,
    surfaceVariant = theme_surfaceVariant,
    onSurface = theme_onSurface,
    onSurfaceVariant = theme_onSurfaceVariant,
    background = theme_background,
    onBackground = theme_onBackground,
    outline = theme_outline
)

private val LightColorScheme = lightColorScheme(
    primary = theme_primary,
    onPrimary = theme_onPrimary,
    primaryContainer = theme_primaryContainer,
    onPrimaryContainer = theme_onPrimaryContainer,
    secondary = theme_secondary,
    onSecondary = theme_onSecondary,
    secondaryContainer = theme_secondaryContainer,
    onSecondaryContainer = theme_onSecondaryContainer,
    tertiary = theme_tertiary,
    surface = theme_surface,
    surfaceVariant = theme_surfaceVariant,
    onSurface = theme_onSurface,
    onSurfaceVariant = theme_onSurfaceVariant,
    background = theme_background,
    onBackground = theme_onBackground,
    outline = theme_outline
)

@Composable
fun HowAreYouTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = MyShapes
    )
}