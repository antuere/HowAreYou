package antuere.how_are_you.presentation.screens.mental_tips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.mental_tips.ui_compose.MentalTipsScreenContent
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MentalTipsScreen(
    viewModel: MentalTipsViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(viewState.appBarTitleId) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(viewState.appBarTitleId),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    MentalTipsScreenContent(viewState = { viewState })
}