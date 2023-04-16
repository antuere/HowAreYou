package antuere.how_are_you.presentation.screens.detail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent
import antuere.how_are_you.presentation.screens.detail.state.DetailSideEffect
import antuere.how_are_you.presentation.screens.detail.ui_compose.DetailScreenState
import antuere.how_are_you.presentation.screens.detail.ui_compose.DetailScreenTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val focusManager = LocalFocusManager.current
    val rotation = remember { Animatable(initialValue = 0F) }
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            DetailSideEffect.AnimateFavoriteBtn -> {
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(durationMillis = 450),
                )
                rotation.snapTo(0F)
            }

            is DetailSideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            DetailSideEffect.NavigateUp -> appState.navigateUp()
            DetailSideEffect.ClearFocus -> focusManager.clearFocus()
        }
    }

    BackHandler(viewState.isEditMode) {
        viewModel.onIntent(DetailIntent.EditModeOff)
    }

    DetailScreenTopBar(
        favoriteBtnRes = { viewState.favoriteBtnRes },
        rotation = { rotation.value },
        isEditMode = { viewState.isEditMode },
        onIntent = { viewModel.onIntent(it) }
    )

    DetailScreenState(
        onIntent = { viewModel.onIntent(it) },
        isLoading = viewState.isLoading,
        isEditMode = viewState.isEditMode,
        daySmileImage = viewState.daySmileRes,
        dateString = viewState.dateString,
        dayText = viewState.dayText,
        dayTextEditable = viewState.dayTextEditable
    )
}