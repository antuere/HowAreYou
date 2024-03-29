package antuere.how_are_you.presentation.screens.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.favorites.state.FavoritesSideEffect
import antuere.how_are_you.presentation.screens.favorites.ui_compose.FavoritesScreenContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun FavoritesScreen(
    onNavigateToDetail: (Long) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val hapticFeedback = LocalHapticFeedback.current
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.favorites),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is FavoritesSideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            is FavoritesSideEffect.NavigationToDayDetail -> onNavigateToDetail(sideEffect.dayId)
            FavoritesSideEffect.Vibration -> appState.vibratePhone(hapticFeedback)
        }
    }

    FavoritesScreenContent(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}