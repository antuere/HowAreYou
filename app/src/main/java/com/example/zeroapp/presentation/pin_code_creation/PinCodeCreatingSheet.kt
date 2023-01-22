package com.example.zeroapp.presentation.pin_code_creation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.pin_code.NumericKeyPad
import com.example.zeroapp.presentation.base.ui_compose_components.pin_code.PinCirclesIndicates
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PinCodeCreating(
    sheetViewModel: PinCodeCreatingSheetViewModel = hiltViewModel(),
    bottomSheetState: ModalBottomSheetState,
    onHandleResult: (Boolean) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pinCodeCirclesState by sheetViewModel.pinCodeCirclesState.collectAsState()
    val isPinCodeCreated by sheetViewModel.isPinCodeCreated.collectAsState()

    val isEnabledHandler = bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        if (bottomSheetState.targetValue == ModalBottomSheetValue.Hidden) {
            onHandleResult(isPinCodeCreated)
            sheetViewModel.resetAllPinCodeStates()
        }
    }

    LaunchedEffect(bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            sheetViewModel.resetIsPinCodeCreated()
        }
    }

    if (isPinCodeCreated) {
        LaunchedEffect(true) {
            bottomSheetState.hide()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Image(
            painter = painterResource(id = R.drawable.ic_horizontal_line),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        Text(text = stringResource(id = R.string.create_a_pin_code))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        PinCirclesIndicates(pinCodeCirclesState)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

        NumericKeyPad(
            onClick = { sheetViewModel.onClickNumber(it) },
            onClickClear = { sheetViewModel.resetAllPinCodeStates() })

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_7)))
    }
}
