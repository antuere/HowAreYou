package antuere.how_are_you.presentation.screens.onboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.onboard.state.OnboardSideEffect
import antuere.how_are_you.presentation.screens.onboard.ui_compose.OnboardScreenContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun OnboardScreen(
    onNavigateHomeScreen: () -> Unit,
    viewModel: OnboardViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                isVisibleTopBar = false,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            OnboardSideEffect.NavigateToHome -> onNavigateHomeScreen()
        }
    }

    OnboardScreenContent(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) }
    )
}