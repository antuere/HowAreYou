package antuere.how_are_you.presentation.screens.settings.ui_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.pin_code_creation.PinCreatingSheet
import antuere.how_are_you.presentation.screens.settings.state.SettingsIntent
import antuere.how_are_you.presentation.screens.settings.state.SettingsState
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.AuthSection
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.GeneralSettings
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.InfoAboutApp
import antuere.how_are_you.presentation.screens.settings.ui_compose.components.PrivacySettings
import antuere.how_are_you.util.extensions.paddingBotAndTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    viewState: () -> SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    bottomSheetState: SheetState,
) {
    if (viewState().isShowBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onIntent(SettingsIntent.PinCreationSheetClosed(false)) },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.onPrimary,
            windowInsets = WindowInsets(0)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PinCreatingSheet(
                    onHandleResult = { onIntent(SettingsIntent.PinCreationSheetClosed(it)) },
                )
            }
        }
    }

    if (viewState().isLoading) {
        FullScreenProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingBotAndTopBar()
                .verticalScroll(rememberScrollState())
        ) {
            AuthSection(
                userName = viewState().userNickname,
                onClickSignIn = { onIntent(SettingsIntent.SignInBtnClicked) },
                onClickAccountSettings = { onIntent(SettingsIntent.AccountSettingsBtnClicked) },
                onClickSignInAdvice = { onIntent(SettingsIntent.SignInAdviceClicked) }
            )
            GeneralSettings(
                isCheckedWorriedDialog = viewState().isCheckedWorriedDialog,
                onCheckedChangeWorriedDialog = {
                    onIntent(SettingsIntent.WorriedDialogSettingChanged(it))
                },
                onClickDayCustomization = {
                    onIntent(SettingsIntent.DayCustomizationClicked)
                }
            )
            PrivacySettings(
                isCheckedPinCode = viewState().isCheckedPin,
                checkChangePinCode = { onIntent(SettingsIntent.PinSettingChanged(it)) },
                isShowBiometricSetting = viewState().isEnableBiomAuthOnDevice && viewState().userPinCode.length == 4,
                isCheckedBiometric = viewState().isCheckedBiomAuth,
                checkChangeBiometric = { onIntent(SettingsIntent.BiometricAuthSettingChanged(it)) },
                onClickPrivacyPolicy = { onIntent(SettingsIntent.PrivacyPolicyClicked) }
            )

            InfoAboutApp(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }
    }
}