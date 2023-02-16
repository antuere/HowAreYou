package antuere.how_are_you.presentation.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.settings.state.SettingsSideEffect
import antuere.how_are_you.presentation.screens.settings.ui_compose.SettingsScreenState
import antuere.how_are_you.util.extensions.findFragmentActivity
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    onNavigateSignIn: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : composed in settings screen")
    val appState = LocalAppState.current
    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewState by viewModel.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.settings,
                isVisibleBottomBar = true
            )
        )
        appState.dismissSnackbar()
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
            is SettingsSideEffect.NavigateToSignIn -> onNavigateSignIn()
            is SettingsSideEffect.BiometricNoneEnroll -> {
                launcher.launch(sideEffect.enrollIntent)
            }
            SettingsSideEffect.ShowBottomSheet -> {
                scope.launch {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
                appState.changeVisibilityBottomBar(false)
            }
            SettingsSideEffect.HideBottomSheet -> {
                scope.launch { bottomSheetState.hide() }
                appState.changeVisibilityBottomBar(true)
            }
        }
    }

    SettingsScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        bottomSheetState = bottomSheetState
    )
}
