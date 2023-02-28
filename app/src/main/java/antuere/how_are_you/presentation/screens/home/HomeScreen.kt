package antuere.how_are_you.presentation.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.home.state.HomeSideEffect
import antuere.how_are_you.presentation.screens.home.ui_compose.HomeScreenState
import antuere.how_are_you.util.extensions.findFragmentActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HomeScreen(
    onNavigateToMentalTips: () -> Unit,
    onNavigateToHelpForYou: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCats: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToAddDay: () -> Unit,
    viewModel: () -> HomeViewModel,
) {
    Timber.i("MVI error test : enter in home screen, view model is ${viewModel().toString()}")
    val context = LocalContext.current
    val appState = LocalAppState.current
    val viewState by viewModel().collectAsState()

    val fragmentActivity = LocalContext.current.findFragmentActivity()
    BackHandler {
        fragmentActivity.finish()
    }

    viewModel().collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.Dialog -> {
                appState.showDialog(sideEffect.uiDialog)
            }
            is HomeSideEffect.NavigateToDayDetail -> {
                onNavigateToDetail(sideEffect.dayId)
            }
            is HomeSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
            HomeSideEffect.NavigateToAddDay -> onNavigateToAddDay()
            HomeSideEffect.NavigateToCats -> onNavigateToCats()
            HomeSideEffect.NavigateToFavorites -> onNavigateToFavorites()
            HomeSideEffect.NavigateToHelpForYou -> onNavigateToHelpForYou()
            HomeSideEffect.NavigateToMentalTips -> onNavigateToMentalTips()
        }
    }

    LaunchedEffect(true) {
        appState.updateAppBar(AppBarState(titleId = R.string.home))
        appState.dismissSnackbar()
    }

    HomeScreenState(viewState = { viewState }, onIntent = { viewModel().onIntent(it) })
}