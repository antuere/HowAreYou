package antuere.how_are_you.presentation.screens.sign_in_methods

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsIntent
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsSideEffect
import antuere.how_are_you.presentation.screens.sign_in_methods.ui_compose.SignInMethodsScreenState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SignInMethodsScreen(
    onNavigateSignInEmail: () -> Unit,
    viewModel: SignInMethodsViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val context = LocalContext.current
    val viewState by viewModel.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                SignInMethodsIntent.GoogleAccAdded(task).run(viewModel::onIntent)
            }
        }

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.login_methods),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignInMethodsSideEffect.GoogleSignInDialog -> {
                launcher.launch(sideEffect.signInClient.signInIntent)
            }
            SignInMethodsSideEffect.NavigateToEmailMethod -> onNavigateSignInEmail()
            SignInMethodsSideEffect.NavigateUp -> appState.navigateUp()
            is SignInMethodsSideEffect.Snackbar -> {
                appState.showSnackbar(
                    sideEffect.message.asString(context)
                )
            }
        }
    }

    SignInMethodsScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}