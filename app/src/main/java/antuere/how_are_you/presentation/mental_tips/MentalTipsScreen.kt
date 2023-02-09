package antuere.how_are_you.presentation.mental_tips

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.mental_tips.ui_compose.MentalTipsScreenState
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber

@Composable
fun MentalTipsScreen(
    viewModel: MentalTipsViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in mental tips screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(viewState.appBarTitleId) {
        appState.updateAppBar(
            AppBarState(
                titleId = viewState.appBarTitleId,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    MentalTipsScreenState(viewState = { viewState })
}