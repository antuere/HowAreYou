package com.example.zeroapp.presentation.pin_code_сreating

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.NumericKeyPad
import com.example.zeroapp.presentation.settings.ui_compose.ProgressCircle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PinCodeCreating(
    sheetViewModel: PinCodeCreatingSheetViewModel = hiltViewModel(),
    bottomSheetState: ModalBottomSheetState,
    changeCheckPinCode: (Boolean) -> Unit
) {
    val pinCodeCirclesState by sheetViewModel.pinCodeCirclesState.collectAsState()
    val isPinCodeCreated by sheetViewModel.isPinCodeCreated.collectAsState()

    LaunchedEffect(bottomSheetState.targetValue) {
        if (bottomSheetState.targetValue == ModalBottomSheetValue.Hidden) {
            if (!isPinCodeCreated) {
                changeCheckPinCode(false)
            }
        }
    }


    if (isPinCodeCreated) {
        LaunchedEffect(key1 = true) {
            bottomSheetState.hide()
            sheetViewModel.resetIsPinCodeCreated()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_10)))

        Text(text = stringResource(id = R.string.create_a_pin_code))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))
        Row() {
            when (pinCodeCirclesState) {
                PinCodeCirclesState.NONE -> {
                    repeat(4) {
                        ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                    }
                }
                PinCodeCirclesState.FIRST -> {
                    ProgressCircle(drawId = R.drawable.ic_circle_filled)
                    repeat(3) {
                        ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                    }
                }
                PinCodeCirclesState.SECOND -> {
                    ProgressCircle(drawId = R.drawable.ic_circle_filled)
                    ProgressCircle(drawId = R.drawable.ic_circle_filled)
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                }
                PinCodeCirclesState.THIRD -> {
                    repeat(3) {
                        ProgressCircle(drawId = R.drawable.ic_circle_filled)
                    }
                    ProgressCircle(drawId = R.drawable.ic_circle_outlined)
                }
                PinCodeCirclesState.FOURTH, PinCodeCirclesState.ALL -> {
                    repeat(4) {
                        ProgressCircle(drawId = R.drawable.ic_circle_filled)
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

        NumericKeyPad(
            onClick = { sheetViewModel.onClickNumber(it) },
            onClickClear = { sheetViewModel.resetEnteredPinCode() })

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))
    }
}