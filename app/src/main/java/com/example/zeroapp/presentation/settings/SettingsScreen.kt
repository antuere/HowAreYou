package com.example.zeroapp.presentation.settings

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.settings.ui_compose.AuthSection
import com.example.zeroapp.presentation.settings.ui_compose.GeneralSettings
import com.example.zeroapp.presentation.pin_code_creation.PinCodeCreating
import com.example.zeroapp.presentation.settings.ui_compose.PrivacySettings
import com.example.zeroapp.util.ShowSnackBarWithDelay
import com.example.zeroapp.util.findFragmentActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateSignIn: () -> Unit,
    snackbarHostState: SnackbarHostState,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val scope = rememberCoroutineScope()

    val settings by settingsViewModel.settings.collectAsState()
    val userPinCode by settingsViewModel.savedPinCode.collectAsState()
    val uiBiometricDialog = settingsViewModel.uiBiometricDialog
    val uiDialog by settingsViewModel.uiDialog.collectAsState()
    val userNickName by settingsViewModel.userNickname.collectAsState()
    val biometricsAvailableState by settingsViewModel.biometricAvailableState.collectAsState()
    val biometricAuthState by settingsViewModel.biometricAuthState.collectAsState()

    val paddingNormal = dimensionResource(id = R.dimen.padding_normal_3)

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    var isCheckedWorriedDialog by remember {
        mutableStateOf(settings?.isShowWorriedDialog ?: true)
    }

    var isShowBiometricAuthSuccessSnackBar by remember {
        mutableStateOf(false)
    }

    var isShowNoneEnrollBiometricAuthSnackBar by remember {
        mutableStateOf(false)
    }

    var isShowPinCodeSuccessCreatedSnackBar by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        onComposing(
            AppBarState(
                titleId = R.string.settings
            ),
            bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
        )
    }

    var isCheckedPinCode by remember {
        mutableStateOf(
            settings?.isPinCodeEnabled ?: false
        )
    }

    var isCheckedBiometric by remember {
        mutableStateOf(
            settings?.isBiometricEnabled ?: false
        )
    }

    if (bottomSheetState.isVisible) {
        userPinCode?.let { pin ->
            isCheckedPinCode = pin.length == 4
        }
    }

    if (isShowBiometricAuthSuccessSnackBar) {
        snackbarHostState.ShowSnackBarWithDelay(
            message = stringResource(id = R.string.biom_auth_create_success),
            hideSnackbarAfterDelay = { isShowBiometricAuthSuccessSnackBar = false }
        )
    }
    if (isShowNoneEnrollBiometricAuthSnackBar) {
        snackbarHostState.ShowSnackBarWithDelay(
            message = stringResource(id = R.string.biometric_none_enroll),
            hideSnackbarAfterDelay = { isShowNoneEnrollBiometricAuthSnackBar = false }
        )
    }

    if (isShowPinCodeSuccessCreatedSnackBar) {
        snackbarHostState.ShowSnackBarWithDelay(
            message = stringResource(id = R.string.pin_code_create_success),
            hideSnackbarAfterDelay = { isShowPinCodeSuccessCreatedSnackBar = false })
    }

    biometricAuthState?.let { biomAuthState ->
        when (biomAuthState) {
            BiometricAuthState.SUCCESS -> {
                isCheckedBiometric = true
                settingsViewModel.saveSettings(
                    isUseBiometric = true,
                    isUsePinCode = true
                )
                isShowBiometricAuthSuccessSnackBar = true
            }
            BiometricAuthState.ERROR -> {
                isCheckedBiometric = false
            }
        }
        settingsViewModel.nullifyBiometricAuthState()
    }

    biometricsAvailableState?.let { availableState ->
        when (availableState) {
            is BiometricsAvailableState.NoneEnrolled -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                        )
                    }
                    val launcher =
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}
                    SideEffect {
                        launcher.launch(enrollIntent)
                    }
                } else {
                    isShowNoneEnrollBiometricAuthSnackBar = true
                    isCheckedBiometric = false
                }
            }
            else -> {}
        }
        settingsViewModel.nullifyBiometricAvailableState()
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
                    bottomSheetState = bottomSheetState,
                    onShowSuccessSnackBar = { isShowPinCodeSuccessCreatedSnackBar = true })
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            uiDialog?.let {
                Dialog(dialog = it)
            }
            AuthSection(
                modifier = Modifier.padding(
                    horizontal = paddingNormal
                ),
                userName = userNickName,
                onClickSignIn = { onNavigateSignIn() },
                onClickSignOut = { settingsViewModel.onClickSignOut() })

            settings?.let {
                GeneralSettings(
                    modifier = Modifier.padding(
                        horizontal = paddingNormal
                    ),
                    isCheckedWorriedDialog = isCheckedWorriedDialog,
                    checkChangeWorriedDialog = {
                        isCheckedWorriedDialog = it
                        settingsViewModel.saveShowWorriedDialog(it)
                    }
                )

                PrivacySettings(
                    modifier = Modifier.padding(
                        horizontal = paddingNormal
                    ),
                    isCheckedPinCode = isCheckedPinCode,
                    checkChangePinCode = {
                        isCheckedPinCode = it
                        if (it) {
                            scope.launch {
                                bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        } else {
                            settingsViewModel.resetPinCodeAuth()
                            settingsViewModel.resetBiometricAuthAndSaveSettings(
                                isUseBiometric = false,
                                isUsePinCode = false
                            )
                        }
                    },
                    isShowBiometricSetting =
                    biometricsAvailableState !is BiometricsAvailableState.NoHardware && userPinCode?.length == 4,
                    isCheckedBiometric = isCheckedBiometric,
                    checkChangeBiometric = {
                        isCheckedBiometric = it
                        if (it) {
                            uiBiometricDialog.startBiometricAuth(
                                biometricListener = settingsViewModel.biometricAuthStateListener,
                                activity = fragmentActivity
                            )
                        } else {
                            settingsViewModel.resetBiometricAuthAndSaveSettings(
                                isUseBiometric = false,
                                isUsePinCode = true
                            )
                        }
                    }
                )
            }
        }
    }
}
