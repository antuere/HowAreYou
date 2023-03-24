package antuere.how_are_you.presentation.screens.settings.ui_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.pin_code_creation.PinCreatingSheet
import antuere.how_are_you.presentation.screens.settings.state.SettingsIntent
import antuere.how_are_you.presentation.screens.settings.state.SettingsState
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.AuthSection
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.GeneralSettings
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.PrivacySettings
import antuere.how_are_you.util.extensions.paddingBotAndTopBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreenState(
    viewState: () -> SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    bottomSheetState: ModalBottomSheetState,
) {
    val isSheetStartsHiding by remember {
        derivedStateOf {
            bottomSheetState.targetValue == ModalBottomSheetValue.Hidden
                    && bottomSheetState.isAnimationRunning
        }
    }
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.onPrimary,
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PinCreatingSheet(
                    onHandleResult = { onIntent(SettingsIntent.PinCreationSheetClosed(it)) },
                    isSheetStartsHiding = isSheetStartsHiding
                )
            }
        },
    ) {
        if (viewState().isLoading) {
            FullScreenProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .paddingBotAndTopBar()
            ) {
                AuthSection(
                    userName = viewState().userNickname,
                    onClickSignIn = { onIntent(SettingsIntent.SignInBtnClicked) },
                    onClickSignOut = { onIntent(SettingsIntent.SignOutBtnClicked) },
                )
                GeneralSettings(
                    isCheckedWorriedDialog = viewState().isCheckedWorriedDialog,
                    onCheckedChangeWorriedDialog = {
                        onIntent(SettingsIntent.WorriedDialogSettingChanged(it))
                    }
                )
                PrivacySettings(
                    isCheckedPinCode = viewState().isCheckedPin,
                    checkChangePinCode = { onIntent(SettingsIntent.PinSettingChanged(it)) },
                    isShowBiometricSetting = viewState().isEnableBiomAuthOnDevice && viewState().userPinCode.length == 4,
                    isCheckedBiometric = viewState().isCheckedBiomAuth,
                    checkChangeBiometric = { onIntent(SettingsIntent.BiometricAuthSettingChanged(it)) },
                )
            }
        }
    }
}