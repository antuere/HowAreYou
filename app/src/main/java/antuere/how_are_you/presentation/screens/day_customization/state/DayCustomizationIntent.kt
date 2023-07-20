package antuere.how_are_you.presentation.screens.day_customization.state

sealed interface DayCustomizationIntent {
    data class FontSizeChanged(val newFontSize: Int) : DayCustomizationIntent
}