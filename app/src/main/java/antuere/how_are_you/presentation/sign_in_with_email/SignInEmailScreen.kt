package antuere.how_are_you.presentation.sign_in_with_email

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.sign_in_with_email.state.SignInEmailSideEffect
import antuere.how_are_you.presentation.sign_in_with_email.ui_compose.SignInEmailScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun SignInEmailScreen(
    onNavigateSettings: () -> Unit,
    onNavigateSignUp: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    viewModel: SignInEmailViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in signIn screen")

    val context = LocalContext.current
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.sign_in,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignInEmailSideEffect.NavigateToResetPassword -> onNavigateResetPassword()
            SignInEmailSideEffect.NavigateToSettings -> onNavigateSettings()
            SignInEmailSideEffect.NavigateToSignUp -> onNavigateSignUp()
            is SignInEmailSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
        }
    }

    SignInEmailScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}
