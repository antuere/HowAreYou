package antuere.how_are_you.presentation.screens.secure_entry.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.NumericKeypadWrapper
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.PinCirclesIndicatesWrapper
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntryIntent
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntryState
import antuere.how_are_you.util.extensions.paddingTopBar
import timber.log.Timber

@Composable
fun SecureEntryScreenState(
    viewState: () -> SecureEntryState,
    onIntent: (SecureEntryIntent) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
    ) {
        Timber.i("MVI error test : enter in secure column")

        IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
        Spacer(modifier = Modifier.weight(0.2F))

        Text(text = stringResource(id = R.string.enter_a_pin))
        Spacer(modifier = Modifier.weight(0.1F))

        PinCirclesIndicatesWrapper { viewState().pinCirclesState }
        Spacer(modifier = Modifier.weight(0.4F))

        NumericKeypadWrapper(
            onClick = { onIntent(SecureEntryIntent.NumberClicked(it)) },
            onClickClear = { onIntent(SecureEntryIntent.PinStateReset) },
            isShowBiometricBtn = { viewState().isShowBiometricBtn },
            onClickBiometric = { onIntent(SecureEntryIntent.BiometricBtnClicked) },
        )
        Spacer(modifier = Modifier.weight(0.4F))

        TextButton(onClick = { onIntent(SecureEntryIntent.SignOutBtnClicked) }) {
            Text(text = stringResource(id = R.string.sign_out))
        }
        Spacer(modifier = Modifier.weight(0.1F))
    }
}