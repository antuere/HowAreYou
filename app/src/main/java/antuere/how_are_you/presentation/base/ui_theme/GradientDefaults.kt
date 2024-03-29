package antuere.how_are_you.presentation.base.ui_theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

object GradientDefaults {

    @Composable
    fun primary(): Brush {
        return Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.surface,
            )
        )
    }

    @Composable
    fun secondary(): Brush {
        return Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.secondaryContainer,
            )
        )
    }

    @Composable
    fun secondaryInverse(): Brush {
        return Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.surface,
            )
        )

    }

    @Composable
    fun tertiary(): Brush {
        return Brush.linearGradient(
            listOf(
                Color.White.copy(alpha = 0.78F),
                MaterialTheme.colorScheme.surface
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

    @Composable
    fun lightWhite(): Brush {
        return Brush.linearGradient(
            listOf(
                Color.White,
                MaterialTheme.colorScheme.surface,
            )
        )
    }

    @Composable
    fun background(): Brush {
        return Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.background,
            )
        )
    }

    @Composable
    fun gradientsForSmiles(): ImmutableList<Brush> {
        return listOf(
            smileGradient(MaterialTheme.colorScheme.primaryContainer, color_bad_day),
            smileGradient(
                MaterialTheme.colorScheme.primaryContainer,
                color_bad_day.copy(alpha = 0.7F)
            ),
            smileGradient(
                MaterialTheme.colorScheme.primaryContainer,
                color_bad_day.copy(alpha = 0.2F)
            ),
            surface(),
            smileGradient(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.surface
            ),
        ).toImmutableList()
    }

    @Composable
    private fun smileGradient(topColor: Color, botColor: Color): Brush {
        return Brush.linearGradient(
            listOf(topColor, botColor)
        )
    }

    fun shadow(): Brush {
        return Brush.verticalGradient(
            listOf(
                Color.Transparent,
                Color.Black.copy(alpha = 0.1f)
            )
        )
    }
}



