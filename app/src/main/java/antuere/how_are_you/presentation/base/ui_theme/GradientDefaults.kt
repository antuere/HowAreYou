package antuere.how_are_you.presentation.base.ui_theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

object GradientDefaults {

    @Composable
    fun primary(): Brush {
        return Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onPrimary,
            )
        )
    }

    @Composable
    fun secondary(): Brush {
        return Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.onPrimary,
                MaterialTheme.colorScheme.secondaryContainer,
            )
        )
    }

    @Composable
    fun primaryTriple(): Brush {
        return Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.onPrimary,
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onPrimary,
            )
        )
    }
    @Composable
    fun surface(): Brush {
        return Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.surface,
            )
        )
    }
}

