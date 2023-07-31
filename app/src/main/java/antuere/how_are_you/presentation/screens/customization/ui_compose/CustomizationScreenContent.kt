package antuere.how_are_you.presentation.screens.customization.ui_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.divider.DefaultDivider
import antuere.how_are_you.presentation.base.ui_compose_components.settings.SettingItem
import antuere.how_are_you.presentation.screens.customization.state.CustomizationIntent
import antuere.how_are_you.presentation.screens.customization.state.CustomizationState
import antuere.how_are_you.presentation.screens.customization.ui_compose.components.FontSizeSettings
import antuere.how_are_you.presentation.screens.customization.ui_compose.components.ThemeSelectionDialog
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun CustomizationScreenContent(
    viewState: () -> CustomizationState,
    onIntent: (CustomizationIntent) -> Unit,
) {
    if (viewState().isShowThemeDialog) {
        ThemeSelectionDialog(
            onDismissRequest = { onIntent(CustomizationIntent.ThemeDialogClose) },
            onThemeSelected = { onIntent(CustomizationIntent.ThemeSelected(it)) },
            currentValue = viewState().theme
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_normal_1)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (viewState().isLoading) {
            CircularProgressIndicator()
        } else {
            FontSizeSettings(
                fontSize = viewState().fontSize,
                onChangeFontSize = { onIntent(CustomizationIntent.FontSizeChanged(newFontSize = it)) }
            )
            SettingItem(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_normal_1)),
                titleId = R.string.change_the_theme,
                iconId = R.drawable.ic_theme_chooser,
                onClick = { onIntent(CustomizationIntent.ThemeDialogClicked) }
            )
            DefaultDivider()
        }
    }
}



