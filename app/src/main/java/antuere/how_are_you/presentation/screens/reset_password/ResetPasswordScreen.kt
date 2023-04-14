package antuere.how_are_you.presentation.screens.reset_password

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.reset_password.state.ResetPasswordSideEffect
import antuere.how_are_you.presentation.screens.reset_password.ui_compose.ResetPasswordScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val appState = LocalAppState.current
    val focusManager = LocalFocusManager.current

    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.reset_password,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = {
                    focusManager.clearFocus()
                    appState.navigateUp()
                },
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ResetPasswordSideEffect.NavigateUp -> appState.navigateUp()
            is ResetPasswordSideEffect.Snackbar -> appState.showSnackbar(
                sideEffect.message.asString(context)
            )
            ResetPasswordSideEffect.ClearFocus -> focusManager.clearFocus()
        }
    }

    ResetPasswordScreenState(onIntent = { viewModel.onIntent(it) }, viewState = { viewState })
}
