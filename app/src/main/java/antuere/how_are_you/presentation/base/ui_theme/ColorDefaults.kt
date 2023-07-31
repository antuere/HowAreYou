package antuere.how_are_you.presentation.base.ui_theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter

object ColorDefaults {
    @Composable
    fun themeFilter(): ColorFilter {
        return if (isSystemInDarkTheme()) {
            ColorFilter.lighting(
                MaterialTheme.colorScheme.onBackground,
                MaterialTheme.colorScheme.inverseSurface,
            )
        } else {
            ColorFilter.lighting(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.inverseSurface,
            )
        }
    }

    @Composable
    fun themeFilterForIconApp(): ColorFilter {
        return if (isSystemInDarkTheme()) {
            ColorFilter.lighting(
                MaterialTheme.colorScheme.onBackground,
                MaterialTheme.colorScheme.inverseSurface,
            )
        } else {
            ColorFilter.lighting(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.inverseSurface,
            )
        }
    }
}