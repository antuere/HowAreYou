package antuere.how_are_you.presentation.screens.history

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.screens.history.state.HistorySideEffect
import antuere.how_are_you.presentation.screens.history.ui_compose.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    onNavigateToDetail: (Long) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in history screen, view model is ${viewModel.toString()}")
    val appState = LocalAppState.current

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(initialValue = 360f) }
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HistorySideEffect.AnimationHistoryHeader -> {
                rotation.animateTo(
                    targetValue = if (rotation.value == 360F) 0f else 360f,
                    animationSpec = tween(durationMillis = 450)
                )
            }
            is HistorySideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            is HistorySideEffect.NavigationToDayDetail -> onNavigateToDetail(sideEffect.dayId)
            HistorySideEffect.ShowBottomSheet -> {
                appState.changeVisibilityBottomBar(false)
                scope.launch {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
            HistorySideEffect.HideBottomSheet -> {
                appState.changeVisibilityBottomBar(true)
                scope.launch { bottomSheetState.hide() }
            }
        }
    }

    HistoryScreenTopBar(onIntent = { viewModel.onIntent(it) })

    HistoryScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        bottomSheetState = bottomSheetState,
        rotation = { rotation.value }
    )
}

