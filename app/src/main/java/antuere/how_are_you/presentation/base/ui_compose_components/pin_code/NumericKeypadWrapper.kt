package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.compose.runtime.Composable

@Composable
fun NumericKeypadWrapper(
    onClick: (String) -> Unit,
    onClickClear: () -> Unit,
    isShowBiometricBtn: () -> Boolean,
    onClickBiometric: () -> Unit = {},
) {
    NumericKeypad(
        onClick = onClick,
        onClickClear = onClickClear,
        isShowBiometricBtn = isShowBiometricBtn(),
        onClickBiometric = onClickBiometric
    )
}