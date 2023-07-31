package antuere.how_are_you.presentation.screens.customization.state

import antuere.domain.dto.AppTheme

sealed interface CustomizationIntent {
    data class FontSizeChanged(val newFontSize: Int) : CustomizationIntent
    data class ThemeSelected(val theme: AppTheme) : CustomizationIntent
    object ThemeDialogClicked : CustomizationIntent
    object ThemeDialogClose : CustomizationIntent
}