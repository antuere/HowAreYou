package antuere.how_are_you.presentation.cats

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.cats.ui_compose.CatsScreenState
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in catsScreen screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.cats,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    CatsScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}
