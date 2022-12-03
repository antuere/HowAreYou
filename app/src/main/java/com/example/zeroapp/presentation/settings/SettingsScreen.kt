package com.example.zeroapp.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.settings.ui_compose.AuthSection
import timber.log.Timber

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateSignIn : () -> Unit,
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
    val paddingLarge = dimensionResource(id = R.dimen.padding_large_0)

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.settings
            ),
            true
        )
    }

    Column(
        modifier = Modifier
            .padding(
                horizontal = paddingNormal
            )
            .fillMaxSize()
    ) {
        uiDialog?.let {
            Dialog(dialog = it)
        }
        Timber.i("navigate error : enter in settings")
        AuthSection(
            modifier = Modifier.padding(top = paddingLarge),
            userName = userNickName,
            onClickSignIn = { onNavigateSignIn() },
            onClickSignOut = { settingsViewModel.onClickSignOut() })

    }


}