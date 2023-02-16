package antuere.how_are_you.presentation.screens.help_for_you

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouSideEffect
import antuere.how_are_you.presentation.screens.help_for_you.ui_compose.HelpForYouScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HelpForYouScreen(
    onNavigateToHelplines: () -> Unit,
    viewModel: HelpForYouViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in help for u screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.help_for_you,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            HelpForYouSideEffect.NavigateToHelplines -> onNavigateToHelplines()
        }
    }

    HelpForYouScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}
