package com.example.zeroapp.presentation.settings

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
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import com.example.zeroapp.presentation.settings.ui_compose.AuthSection
import com.example.zeroapp.presentation.settings.ui_compose.GeneralSettings
import com.example.zeroapp.presentation.pin_code_creation.PinCodeCreating
import com.example.zeroapp.presentation.settings.state.SettingsSideEffect
import com.example.zeroapp.presentation.settings.ui_compose.PrivacySettings
import com.example.zeroapp.util.findFragmentActivity
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    updateAppBar: (AppBarState) -> Unit,
    showSnackbar: (String) -> Unit,
    dismissSnackbar: () -> Unit,
    showDialog: (UIDialog) -> Unit,
    onNavigateSignIn: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        dismissSnackbar()
    }

    val viewState by settingsViewModel.collectAsState()

    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val hideBottomSheet: () -> Unit = remember {
        { scope.launch { bottomSheetState.hide() } }
    }

    val isEnabledHandler by remember {
        derivedStateOf {
            bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
        }
    }

    val isSheetStartsHiding by remember {
        derivedStateOf {
            bottomSheetState.direction == 1F
        }
    }

    val onCheckedChangeWorriedDialog: (Boolean) -> Unit = remember {
        { isChecked ->
            settingsViewModel.onChangeWorriedDialogSetting(isChecked)
        }
    }

    val onCheckedChangePin: (Boolean) -> Unit = remember {
        { isChecked ->
            settingsViewModel.onChangePinSetting(isChecked)
        }
    }

    val onCheckedChangeBiom: (Boolean) -> Unit = remember {
        { isChecked ->
            settingsViewModel.onChangeBiomAuthSetting(isChecked)
        }
    }

    val onHandlePinCreationResult: (Boolean) -> Unit = remember {
        { settingsViewModel.onHandlePinCreationResult(it) }
    }

    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        updateAppBar(
            AppBarState(
                titleId = R.string.settings,
                isVisibleBottomBar = bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
            ),
        )
    }

    settingsViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingsSideEffect.Dialog -> {
                showDialog(sideEffect.uiDialog)
            }
            is SettingsSideEffect.Snackbar -> {
                showSnackbar(sideEffect.message.asString(context))
            }
            is SettingsSideEffect.BiometricDialog -> {
                sideEffect.dialog.startBiometricAuth(
                    biometricListener = settingsViewModel.biometricAuthStateListener,
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
                    onHandleResult = onHandlePinCreationResult,
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
            ) {
                AuthSection(
                    userName = viewState.userNickname,
                    onClickSignIn = settingsViewModel::onClickSignIn,
                    onClickSignOut = settingsViewModel::onClickSignOut
                )

                GeneralSettings(
                    isCheckedWorriedDialog = viewState.isCheckedWorriedDialog,
                    onCheckedChangeWorriedDialog = onCheckedChangeWorriedDialog
                )

                PrivacySettings(
                    isCheckedPinCode = viewState.isCheckedPin,
                    checkChangePinCode = onCheckedChangePin,
                    isShowBiometricSetting = viewState.isEnableBiomAuthOnDevice && viewState.userPinCode.length == 4,
                    isCheckedBiometric = viewState.isCheckedBiomAuth,
                    checkChangeBiometric = onCheckedChangeBiom
                )
            }
        }
    }
}
