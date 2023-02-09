package antuere.how_are_you.presentation.history

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.history.state.HistoryIntent
import antuere.how_are_you.presentation.history.state.HistorySideEffect
import antuere.how_are_you.presentation.history.ui_compose.*
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

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(initialValue = 360f) }
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    IconButton(
                        modifier = Modifier.graphicsLayer {
                            scaleY = scaleFilterBtn
                            scaleX = scaleFilterBtn
                        },
                        onClick = { viewModel.onIntent(HistoryIntent.FilterBtnClicked) },
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null
                        )
                    }
                },
                isVisibleBottomBar = true
            ),
        )
        appState.dismissSnackbar()
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HistorySideEffect.AnimationHistoryHeader -> {
                rotation.animateTo(
                    targetValue = if (rotation.value == 360F) 0f else 360f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
            is HistorySideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            is HistorySideEffect.NavigationToDayDetail -> onNavigateToDetail(sideEffect.dayId)
            HistorySideEffect.ShowBottomSheet -> {
                scope.launch {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
                appState.changeVisibilityBottomBar(false)
            }
            HistorySideEffect.HideBottomSheet -> {
                scope.launch { bottomSheetState.hide() }
                appState.changeVisibilityBottomBar(true)
            }
        }
    }

    HistoryScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        bottomSheetState = bottomSheetState,
        rotation = { rotation.value }
    )
}

