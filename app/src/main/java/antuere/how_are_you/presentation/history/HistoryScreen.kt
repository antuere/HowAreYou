package antuere.how_are_you.presentation.history

import androidx.activity.compose.BackHandler
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
    Timber.i("MVI error test : enter in history screen")
    val appState = LocalAppState.current
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(initialValue = 360f) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleFilterBtn by animateFloatAsState(if (isPressed) 0.75f else 1f)

    val viewState by viewModel.collectAsState()

    val isEnabledHandler = remember(bottomSheetState.currentValue) {
        bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    }

    val hideBottomSheet: () -> Unit = remember {
        { scope.launch { bottomSheetState.hide() } }
    }

    val showBottomSheet: () -> Unit = remember {
        { scope.launch { bottomSheetState.animateTo(ModalBottomSheetValue.Expanded) } }
    }

    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(true) {
        appState.dismissSnackbar()
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.history,
                actions = {
                    IconButton(
                        modifier = Modifier.graphicsLayer {
                            scaleY = scaleFilterBtn
                            scaleX = scaleFilterBtn
                        },
                        onClick = showBottomSheet,
                        interactionSource = interactionSource
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null
                        )
                    }
                },
                isVisibleBottomBar = bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
            ),
        )
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
        }
    }

    HistoryScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        bottomSheetState = { bottomSheetState },
        hideBottomSheet = hideBottomSheet,
        rotation = { rotation.value }
    )
}

