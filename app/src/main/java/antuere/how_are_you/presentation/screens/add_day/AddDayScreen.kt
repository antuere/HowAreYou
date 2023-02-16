package antuere.how_are_you.presentation.screens.add_day

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.screens.add_day.ui_compose.AddDayScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun AddDayScreen(
    viewModel: AddDayViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in add day screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.today,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AddDaySideEffect.NavigateUp -> appState.navigateUp()
        }
    }

    AddDayScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}