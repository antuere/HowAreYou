package com.example.zeroapp.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.bottom_sheet_scaffold.BottomSheetScaffold
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.settings.ui_compose.AuthSection
import com.example.zeroapp.presentation.settings.ui_compose.GeneralSettings
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
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
    val biometricsAvailableState by settingsViewModel.biometricAvailableState.collectAsState()
    val biometricAuthState by settingsViewModel.biometricAuthState.collectAsState()

    val isPinCodeCreated by settingsViewModel.isPinCodeCreated.collectAsState()
    val isStartSetBiometric by settingsViewModel.isStartSetBiometric.collectAsState()
    val isStartSetPinCode by settingsViewModel.isStartSetPinCode.collectAsState()

    val paddingNormal = dimensionResource(id = R.dimen.padding_normal_3)

    var isCheckedWorriedDialog by remember {
        mutableStateOf(settings?.isShowWorriedDialog ?: true)
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.settings
            ),
            true
        )
    }
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
        }

    }


}