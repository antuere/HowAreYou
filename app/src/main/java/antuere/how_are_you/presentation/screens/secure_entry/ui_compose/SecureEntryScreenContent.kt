package antuere.how_are_you.presentation.screens.secure_entry.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.NumericKeypadWrapper
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.PinCirclesIndicatesWrapper
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntryIntent
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntryState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun SecureEntryScreenContent(
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
        IconApp()
        Spacer(modifier = Modifier.weight(0.9F))

        Text(text = stringResource(id = R.string.enter_a_pin))
        Spacer(modifier = Modifier.weight(0.2F))

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
            Text(
                text = stringResource(id = R.string.sign_out),
                fontSize = 14f.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.weight(0.1F))
    }
}