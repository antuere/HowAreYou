package antuere.how_are_you.presentation.screens.add_day

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.screens.add_day.ui_compose.AddDayScreenContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddDayScreen(
    viewModel: AddDayViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()
    val focusManager = LocalFocusManager.current

    appState.DisableBackBtnWhileTransitionAnimate()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.today),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = {
                    focusManager.clearFocus()
                    appState.navigateUp()
                },
                isVisibleBottomBar = false,
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AddDaySideEffect.NavigateUp -> {
                focusManager.clearFocus()
                appState.navigateUp()
            }
        }
    }

    AddDayScreenContent(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}