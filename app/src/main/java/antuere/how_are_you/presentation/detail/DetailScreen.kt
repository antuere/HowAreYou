package antuere.how_are_you.presentation.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.detail.state.DetailSideEffect
import antuere.how_are_you.presentation.detail.ui_compose.DetailDay
import antuere.how_are_you.presentation.detail.ui_compose.DetailTopBar
import antuere.how_are_you.util.extensions.toStable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in detail screen")
    val appState = LocalAppState.current
    val rotation = remember { Animatable(initialValue = 0F) }
    val viewState by viewModel.collectAsState()

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
        }
    }

    DetailTopBar(
        favoriteBtnRes = { viewState.favoriteBtnRes },
        rotation = { rotation.value },
        onIntent = { viewModel.onIntent(it) }
    )

    DetailDay(
        isLoading = viewState.isLoading,
        daySmileImage = viewState.daySmileRes,
        dateString = viewState.dateString,
        dayText = viewState.dayText
    )
}