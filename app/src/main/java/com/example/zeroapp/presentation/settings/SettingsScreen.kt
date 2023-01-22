package com.example.zeroapp.presentation.settings

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
import androidx.compose.ui.res.dimensionResource
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

    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val paddingNormal = dimensionResource(id = R.dimen.padding_normal_3)
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val viewState by settingsViewModel.collectAsState()

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
//    val settings by settingsViewModel.settings.collectAsState()
//    val userPinCode by settingsViewModel.savedPinCode.collectAsState()
//    val uiBiometricDialog = settingsViewModel.uiBiometricDialog
//    val uiDialog by settingsViewModel.uiDialog.collectAsState()
//    val userNickName by settingsViewModel.userNickname.collectAsState()
//    val biometricsAvailableState by settingsViewModel.biometricAvailableState.collectAsState()
//    val biometricAuthState by settingsViewModel.biometricAuthState.collectAsState()

//    var isCheckedWorriedDialog by remember {
//        mutableStateOf(settings?.isShowWorriedDialog ?: true)
//    }

    LaunchedEffect(bottomSheetState.targetValue) {
        updateAppBar(
            AppBarState(
                titleId = R.string.settings,
                isVisibleBottomBar = bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
            ),
        )
    }

//    var isCheckedPinCode by remember {
//        mutableStateOf(
//            settings?.isPinCodeEnabled ?: false
//        )
//    }
//
//    var isCheckedBiometric by remember {
//        mutableStateOf(
//            settings?.isBiometricEnabled ?: false
//        )
//    }

//    SideEffect {
//        if (bottomSheetState.isVisible) {
//            userPinCode?.let { pin ->
//                isCheckedPinCode = pin.length == 4
//            }
//        }
//    }

//    LaunchedEffect(biometricAuthState) {
//        biometricAuthState?.let { biomAuthState ->
//            when (biomAuthState) {
//                is BiometricAuthState.Success -> {
//                    isCheckedBiometric = true
//                    settingsViewModel.saveSettings(
//                        isUseBiometric = true,
//                        isUsePinCode = true
//                    )
//                    showSnackbar(biomAuthState.message.asString(context))
//                }
//                BiometricAuthState.Error -> {
//                    isCheckedBiometric = false
//                }
//            }
//            settingsViewModel.nullifyBiometricAuthState()
//        }
//    }

//    LaunchedEffect(biometricsAvailableState) {
//        biometricsAvailableState?.let { availableState ->
//            when (availableState) {
//                is BiometricsAvailableState.NoneEnrolled -> {
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//                        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                            putExtra(
//                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
//                            )
//                        }
//                        launcher.launch(enrollIntent)
//
//                    } else {
//                        showSnackbar(availableState.message.asString(context))
//                        isCheckedBiometric = false
//                    }
//                }
//                else -> {}
//            }
//            settingsViewModel.nullifyBiometricAvailableState()
//        }
//    }

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
                    bottomSheetState = bottomSheetState,
                    onHandleResult = { settingsViewModel.onHandlePinCreationResult(it) })
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (viewState.isLoading) {
                FullScreenProgressIndicator()
            } else {
                AuthSection(
                    modifier = Modifier.padding(
                        horizontal = paddingNormal
                    ),
                    userName = viewState.userNickname,
                    onClickSignIn = { onNavigateSignIn() },
                    onClickSignOut = { settingsViewModel.onClickSignOut() })

                GeneralSettings(
                    modifier = Modifier.padding(
                        horizontal = paddingNormal
                    ),
                    isCheckedWorriedDialog = viewState.isCheckedWorriedDialog,
                    checkChangeWorriedDialog = {
                        settingsViewModel.saveShowWorriedDialog(it)
                    }
                )

                PrivacySettings(
                    modifier = Modifier.padding(
                        horizontal = paddingNormal
                    ),
                    isCheckedPinCode = viewState.isCheckedPin,
                    checkChangePinCode = {
                        settingsViewModel.onChangePinSetting(it)
//                        if (it) {
//                            scope.launch {
//                                bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
//                            }
//                        } else {
//                            settingsViewModel.resetPinCodeAuth()
//                            settingsViewModel.resetBiometricAuthAndSaveSettings(
//                                isUseBiometric = false,
//                                isUsePinCode = false
//                            )
//                        }
                    },
                    isShowBiometricSetting = viewState.isEnableBiomAuthOnDevice && viewState.userPinCode.length == 4,
//                    biometricsAvailableState !is BiometricsAvailableState.NoHardware && userPinCode?.length == 4,
                    isCheckedBiometric = viewState.isCheckedBiomAuth,
                    checkChangeBiometric = {
                        settingsViewModel.onChangeBiomAuthSetting(it)
//                        isCheckedBiometric = it
//                        if (it) {
//                            uiBiometricDialog.startBiometricAuth(
//                                biometricListener = settingsViewModel.biometricAuthStateListener,
//                                activity = fragmentActivity
//                            )
//                        } else {
//                            settingsViewModel.resetBiometricAuthAndSaveSettings(
//                                isUseBiometric = false,
//                                isUsePinCode = true
//                            )
//                        }
                    }
                )
            }

//            settings?.let { savedSettings ->
//                LaunchedEffect(true) {
//                    isCheckedWorriedDialog = savedSettings.isShowWorriedDialog
//                    isCheckedPinCode = savedSettings.isPinCodeEnabled
//                    isCheckedBiometric = savedSettings.isBiometricEnabled
//                }
        }
    }
}
