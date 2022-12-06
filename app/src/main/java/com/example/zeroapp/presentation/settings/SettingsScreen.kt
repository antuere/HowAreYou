package com.example.zeroapp.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.settings.ui_compose.AuthSection
import com.example.zeroapp.presentation.settings.ui_compose.GeneralSettings
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCreating
import com.example.zeroapp.presentation.settings.ui_compose.PrivacySettings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateSignIn: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val uiBiometricDialog = settingsViewModel.uiBiometricDialog
    val uiDialog by settingsViewModel.uiDialog.collectAsState()
    val userNickName by settingsViewModel.userNickname.collectAsState()
    val settings by settingsViewModel.settings.collectAsState()
    val userPinCode by settingsViewModel.savedPinCode.collectAsState()
    val biometricsAvailableState by settingsViewModel.biometricAvailableState.collectAsState()
    val biometricAuthState by settingsViewModel.biometricAuthState.collectAsState()

    val isPinCodeCreated by settingsViewModel.isPinCodeCreated.collectAsState()
    val isStartSetBiometric by settingsViewModel.isStartSetBiometric.collectAsState()
    val isStartSetPinCode by settingsViewModel.isStartSetPinCode.collectAsState()

    val paddingNormal = dimensionResource(id = R.dimen.padding_normal_3)

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()

    var isCheckedWorriedDialog by remember {
        mutableStateOf(settings?.isShowWorriedDialog ?: true)
    }

    var isCheckedPinCode by remember {
        mutableStateOf(settings?.isPinCodeEnabled ?: false)
    }

    var isCheckedBiometric by remember {
        mutableStateOf(settings?.isBiometricEnabled ?: false)
    }

//    userPinCode?.let { pin ->
//        isCheckedPinCode = pin.length == 4
//    }

    LaunchedEffect(bottomSheetState.targetValue) {
        onComposing(
            AppBarState(
                titleId = R.string.settings
            ),
            bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
        )
    }

    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PinCodeCreating(bottomSheetState = bottomSheetState, changeCheckPinCode = {
                    isCheckedPinCode = it
                })
            }
        },
        sheetState = bottomSheetState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
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
                    isShowBiometricSetting = biometricsAvailableState is BiometricsAvailableState.Available,
                    isCheckedBiometric = isCheckedBiometric,
                    checkChangeBiometric = { isCheckedBiometric = it }
                )

            }

        }
    }
}