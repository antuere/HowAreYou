package antuere.how_are_you.presentation.screens.history.ui_compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_text.UiText

@Composable
fun HistoryScreenTopBar() {
    val appState = LocalAppState.current

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.history),
                isVisibleBottomBar = true
            )
        )
    }
}