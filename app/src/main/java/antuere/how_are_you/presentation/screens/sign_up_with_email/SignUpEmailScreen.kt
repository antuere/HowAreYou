package antuere.how_are_you.presentation.screens.sign_up_with_email

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
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.sign_up_with_email.state.SignUpEmailSideEffect
import antuere.how_are_you.presentation.screens.sign_up_with_email.ui_compose.SignUpEmailScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignUpEmailScreen(
    onNavigateSettings: () -> Unit,
    viewModel: SignUpEmailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val appState = LocalAppState.current
    val focusManager = LocalFocusManager.current
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.sign_up),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = {
                    focusManager.clearFocus()
                    appState.navigateUp()
                },
                isVisibleBottomBar = false
            ),
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpEmailSideEffect.NavigateToSettings -> onNavigateSettings()
            is SignUpEmailSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }

            SignUpEmailSideEffect.ClearFocus -> focusManager.clearFocus()
        }
    }

    SignUpEmailScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        focusManager = focusManager
    )
}