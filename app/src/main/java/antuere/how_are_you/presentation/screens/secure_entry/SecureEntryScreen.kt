package antuere.how_are_you.presentation.screens.secure_entry

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntrySideEffect
import antuere.how_are_you.presentation.screens.secure_entry.ui_compose.SecureEntryScreenState
import antuere.how_are_you.util.extensions.findFragmentActivity
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SecureEntryScreen(
    onNavigateHomeScreen: () -> Unit,
    viewModel: SecureEntryViewModel = hiltViewModel(),
) {
    val hapticFeedback = LocalHapticFeedback.current
    val appState = LocalAppState.current
    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                isVisibleTopBar = false,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SecureEntrySideEffect.BiometricDialog -> {
                sideEffect.dialog.startBiometricAuth(
                    biometricListener = viewModel.biometricAuthStateListener,
                    activity = fragmentActivity
                )
            }
            is SecureEntrySideEffect.BiometricNoneEnroll -> launcher.launch(sideEffect.enrollIntent)
            is SecureEntrySideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            is SecureEntrySideEffect.NavigateToHome -> onNavigateHomeScreen()
            is SecureEntrySideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
            SecureEntrySideEffect.Vibration -> {
                appState.vibratePhone(hapticFeedback)
            }
        }
    }

    SecureEntryScreenState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) }
    )
}