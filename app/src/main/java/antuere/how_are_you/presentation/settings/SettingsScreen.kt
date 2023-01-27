package antuere.how_are_you.presentation.settings

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.settings.ui_compose.AuthSection
import antuere.how_are_you.presentation.settings.ui_compose.GeneralSettings
import antuere.how_are_you.presentation.pin_code_creation.PinCodeCreating
import antuere.how_are_you.presentation.settings.state.SettingsIntent
import antuere.how_are_you.presentation.settings.state.SettingsSideEffect
import antuere.how_are_you.presentation.settings.ui_compose.PrivacySettings
import antuere.how_are_you.util.findFragmentActivity
import antuere.how_are_you.util.paddingBotAndTopBar
import antuere.how_are_you.util.toStable
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
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val hideBottomSheet: () -> Unit = remember {
        { scope.launch { bottomSheetState.hide() } }
    }

    val isEnabledHandler = remember(bottomSheetState.currentValue) {
        bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    }

    val isSheetStartsHiding by remember {
        derivedStateOf {
            bottomSheetState.direction == 1F
        }
    }

    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(true) {
        appState.dismissSnackbar()
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.settings,
                isVisibleBottomBar = bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
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
            is SettingsSideEffect.NavigateToSignIn -> onNavigateSignIn()
            is SettingsSideEffect.BiometricNoneEnroll -> {
                launcher.launch(sideEffect.enrollIntent)
            }
            is SettingsSideEffect.ShowBottomSheet -> {
                scope.launch {
                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PinCodeCreating(
                    hideBottomSheet = hideBottomSheet,
                    onHandleResult = { isCreated: Boolean ->
                        SettingsIntent.PinCreationSheetClosed(isCreated).run(viewModel::onIntent)
                    }.toStable(),
                    isSheetStartsHiding = isSheetStartsHiding
                )
            }
        },
    ) {
        if (viewState.isLoading) {
            FullScreenProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .paddingBotAndTopBar()
            ) {
                AuthSection(
                    userName = viewState.userNickname,
                    onClickSignIn = { SettingsIntent.SignInBtnClicked.run(viewModel::onIntent) }.toStable(),
                    onClickSignOut = { SettingsIntent.SignOutBtnClicked.run(viewModel::onIntent) }.toStable()
                )

                GeneralSettings(
                    isCheckedWorriedDialog = viewState.isCheckedWorriedDialog,
                    onCheckedChangeWorriedDialog = { isChecked: Boolean ->
                        SettingsIntent.WorriedDialogSettingChanged(isChecked)
                            .run(viewModel::onIntent)
                    }.toStable()
                )

                PrivacySettings(
                    isCheckedPinCode = viewState.isCheckedPin,
                    checkChangePinCode = { isChecked: Boolean ->
                        SettingsIntent.PinSettingChanged(isChecked).run(viewModel::onIntent)
                    }.toStable(),
                    isShowBiometricSetting = viewState.isEnableBiomAuthOnDevice && viewState.userPinCode.length == 4,
                    isCheckedBiometric = viewState.isCheckedBiomAuth,
                    checkChangeBiometric = { isChecked: Boolean ->
                        SettingsIntent.BiometricAuthSettingChanged(isChecked)
                            .run(viewModel::onIntent)
                    }.toStable()
                )
            }
        }
    }
}
