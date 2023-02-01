package antuere.how_are_you.presentation.secure_entry

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.NumericKeyPad
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.PinCirclesIndicates
import antuere.how_are_you.presentation.secure_entry.state.SecureEntryIntent
import antuere.how_are_you.presentation.secure_entry.state.SecureEntrySideEffect
import antuere.how_are_you.util.findFragmentActivity
import antuere.how_are_you.util.paddingTopBar
import antuere.how_are_you.util.toStable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber


@Composable
fun SecureEntryScreen(
    onNavigateHomeScreen: () -> Unit,
    viewModel: SecureEntryViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in secure entry screen")

    val appState = LocalAppState.current
    val fragmentActivity = LocalContext.current.findFragmentActivity()
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                isVisibleTopBar = false,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SecureEntrySideEffect.BiometricDialog -> {
                sideEffect.dialog.startBiometricAuth(
                    biometricListener = viewModel.biometricAuthStateListener,
                    activity = fragmentActivity
                )
            }
            is SecureEntrySideEffect.BiometricNoneEnroll -> launcher.launch(sideEffect.enrollIntent)
            is SecureEntrySideEffect.Dialog -> appState.showDialog(sideEffect.uiDialog)
            is SecureEntrySideEffect.NavigateToHome -> onNavigateHomeScreen()
            is SecureEntrySideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
    ) {
        IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
        Spacer(modifier = Modifier.weight(0.2F))

        Text(text = stringResource(id = R.string.enter_a_pin))
        Spacer(modifier = Modifier.weight(0.1F))

        PinCirclesIndicates(pinCodeCirclesState = viewState.pinCirclesState)
        Spacer(modifier = Modifier.weight(0.4F))

        NumericKeyPad(
            onClick = { number: String ->
                SecureEntryIntent.NumberClicked(number).run(viewModel::onIntent)
            }.toStable(),
            onClickClear = { SecureEntryIntent.PinStateReset.run(viewModel::onIntent) }.toStable(),
            isShowBiometricBtn = viewState.isShowBiometricBtn,
            onClickBiometric = { SecureEntryIntent.BiometricBtnClicked.run(viewModel::onIntent) }.toStable()
        )
        Spacer(modifier = Modifier.weight(0.4F))

        TextButton(onClick = { SecureEntryIntent.SignOutBtnClicked.run(viewModel::onIntent) }.toStable()) {
            Text(text = stringResource(id = R.string.sign_out))
        }
        Spacer(modifier = Modifier.weight(0.1F))
    }
}