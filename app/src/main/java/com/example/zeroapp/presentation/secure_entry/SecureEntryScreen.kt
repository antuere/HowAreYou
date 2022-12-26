package com.example.zeroapp.presentation.secure_entry

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.IconApp
import com.example.zeroapp.presentation.base.ui_compose_components.NumericKeyPad
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_compose_components.pin_code.PinCirclesIndicates
import com.example.zeroapp.util.ShowToast
import com.example.zeroapp.util.findFragmentActivity


@Composable
fun SecureEntryScreen(
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateHomeScreen: () -> Unit,
    secureEntryViewModel: SecureEntryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                isVisible = false
            ),
            false
        )
    }

    val fragmentActivity = LocalContext.current.findFragmentActivity()

    val uiBiometricDialog = secureEntryViewModel.uiBiometricDialog
    val uiDialog by secureEntryViewModel.uiDialog.collectAsState()
    val isShowBiometricAuth by secureEntryViewModel.isShowBiometricAuth.collectAsState()
    val isNavigateToHomeScreen by secureEntryViewModel.isNavigateToHomeScreen.collectAsState()
    val isShowPinCodeError by secureEntryViewModel.isShowErrorToast.collectAsState()
    val biometricsAvailableState by secureEntryViewModel.biometricAvailableState.collectAsState()
    val pinCodeCirclesState by secureEntryViewModel.pinCodeCirclesState.collectAsState()

    uiDialog?.let {
        Dialog(dialog = it)
    }

    if (isShowPinCodeError) {
        ShowToast(text = stringResource(R.string.wrong_pin_code))
        secureEntryViewModel.resetIsShowErrorToast()
    }

    LaunchedEffect(isShowBiometricAuth) {
        if (isShowBiometricAuth) {
            uiBiometricDialog.startBiometricAuth(
                biometricListener = secureEntryViewModel.biometricAuthStateListener,
                activity = fragmentActivity
            )
            secureEntryViewModel.resetIsShowBiometricAuth()
        }
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
                        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                        }
                    SideEffect {
                        launcher.launch(enrollIntent)
                    }
                } else {
                    ShowToast(text = stringResource(id = R.string.biometric_none_enroll))
                }
            }
            else -> {}
        }
        secureEntryViewModel.nullifyBiometricAvailableState()
    }

    LaunchedEffect(isNavigateToHomeScreen) {
        if (isNavigateToHomeScreen) {
            onNavigateHomeScreen()
            secureEntryViewModel.resetIsNavigateToHomeScreen()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        Text(text = stringResource(id = R.string.enter_a_pin))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        PinCirclesIndicates(pinCodeCirclesState)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

        NumericKeyPad(
            onClick = { secureEntryViewModel.onClickNumber(it) },
            onClickClear = { secureEntryViewModel.resetAllPinCodeStates() },
            isShowBiometricBtn = biometricsAvailableState !is BiometricsAvailableState.NoHardware,
            onClickBiometric = { secureEntryViewModel.onClickBiometricBtn() })
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_7)))

        TextButton(onClick = { secureEntryViewModel.onClickSignOut() }) {
            Text(text = stringResource(id = R.string.sign_out))
        }

    }
}