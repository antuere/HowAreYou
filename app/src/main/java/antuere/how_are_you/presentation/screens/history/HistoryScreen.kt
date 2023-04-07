package antuere.how_are_you.presentation.screens.history

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.screens.history.state.HistorySideEffect
import antuere.how_are_you.presentation.screens.history.ui_compose.*
import antuere.how_are_you.util.extensions.findFragmentActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HistoryScreen(
    onNavigateToDetail: (Long) -> Unit,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in history screen, view model is ${viewModel.toString()}")
    val appState = LocalAppState.current

    val activity = LocalContext.current.findFragmentActivity()
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
        }
    }

    LaunchedEffect(true) {
        appState.dismissSnackbar()
    }

    BackHandler {
        activity.finish()
    }

    HistoryScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        rotation = { rotation.value }
    )
}

