package antuere.how_are_you.presentation.screens.customization.state

import antuere.domain.dto.AppTheme

data class CustomizationState(
    val isLoading: Boolean = true,
    val theme: AppTheme = AppTheme.DEFAULT,
    val isShowThemeDialog: Boolean = false,
    val fontSize: Int = 18
)
