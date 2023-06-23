package antuere.how_are_you.presentation.screens.account_settings

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
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsIntent
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsSideEffect
import antuere.how_are_you.presentation.screens.account_settings.ui_compose.AccountSettingsScreenContent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AccountSettingsScreen(
    viewModel: AccountSettingsViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val context = LocalContext.current
    val viewState by viewModel.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                AccountSettingsIntent.GoogleAccAdded(task).run(viewModel::onIntent)
            }
        }

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.account_settings),
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false,
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AccountSettingsSideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            AccountSettingsSideEffect.NavigateToSettings -> appState.navigateUp()
            is AccountSettingsSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }

            is AccountSettingsSideEffect.GoogleSignInDialog -> {
                launcher.launch(sideEffect.signInClient.signInIntent)
            }
        }
    }

    AccountSettingsScreenContent(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
    )
}