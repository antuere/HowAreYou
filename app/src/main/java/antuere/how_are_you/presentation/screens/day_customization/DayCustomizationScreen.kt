package antuere.how_are_you.presentation.screens.day_customization

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.day_customization.ui_compose.DayCustomizationScreenContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun DayCustomizationScreen(
    viewModel: DayCustomizationViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.day_customization),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false,
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
//        when (sideEffect) {
//            is AccountSettingsSideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
//            AccountSettingsSideEffect.NavigateToSettings -> appState.navigateUp()
//            is AccountSettingsSideEffect.Snackbar -> {
//                appState.showSnackbar(sideEffect.message.asString(context))
//            }
//
//        }
    }

    DayCustomizationScreenContent(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
    )
}