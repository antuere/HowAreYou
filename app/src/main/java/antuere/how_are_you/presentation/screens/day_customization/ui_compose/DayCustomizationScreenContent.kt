package antuere.how_are_you.presentation.screens.day_customization.ui_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.day_customization.state.DayCustomizationIntent
import antuere.how_are_you.presentation.screens.day_customization.state.DayCustomizationState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun DayCustomizationScreenContent(
    viewState: () -> DayCustomizationState,
    onIntent: (DayCustomizationIntent) -> Unit,
) {
    if (viewState().isLoading) {
        FullScreenProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(id = R.dimen.padding_normal_1)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FontSizeSettings(
                fontSize = viewState().fontSize,
                onChangeFontSize = { onIntent(DayCustomizationIntent.FontSizeChanged(newFontSize = it)) }
            )


        }
    }
}



