package antuere.how_are_you.presentation.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.util.Constants
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.settings.state.SettingsSideEffect
import antuere.how_are_you.presentation.screens.settings.ui_compose.SettingsScreenContent
import antuere.how_are_you.util.extensions.findFragmentActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateSignIn: () -> Unit,
    onNavigateAccountSettings: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val viewState by viewModel.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            if (it == SheetValue.Hidden) {
                appState.changeVisibilityBottomBar(true)
            }
            true
        }
    )

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                topBarTitle = UiText.StringResource(R.string.settings),
                isVisibleBottomBar = true
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingsSideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            is SettingsSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }

            is SettingsSideEffect.BiometricDialog -> {
                sideEffect.dialog.startBiometricAuth(
                    biometricListener = viewModel.biometricAuthStateListener,
                    activity = fragmentActivity
                )
            }

            SettingsSideEffect.NavigateToSignIn -> onNavigateSignIn()
            SettingsSideEffect.NavigateToAccountSettings -> onNavigateAccountSettings()
            is SettingsSideEffect.BiometricNoneEnroll -> {
                launcher.launch(sideEffect.enrollIntent)
            }

            SettingsSideEffect.HideNavBar -> {
                appState.changeVisibilityBottomBar(false)
            }

            SettingsSideEffect.ShowNavBar -> {
                appState.changeVisibilityBottomBar(true)
            }

            SettingsSideEffect.NavigateToPrivacyPolicyWebSite -> {
                uriHandler.openUri(Constants.PRIVACY_POLICY_LINK)
            }
        }
    }

    SettingsScreenContent(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        bottomSheetState = bottomSheetState
    )
}
