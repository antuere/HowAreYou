package com.example.zeroapp.presentation.history.ui_compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.history.ui_compose.date_picker.EndDatePicker
import com.example.zeroapp.presentation.history.ui_compose.date_picker.StartDatePicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DaysFilterBottomSheet(
    bottomSheetState: ModalBottomSheetState,
    onDaysSelected: (Pair<Long, Long>) -> Unit
) {
    val dialogStartDateState = rememberMaterialDialogState()
    val dialogEndDateState = rememberMaterialDialogState()
    val scope = rememberCoroutineScope()

    val isEnabledHandler = bottomSheetState.currentValue == ModalBottomSheetValue.Expanded
    BackHandler(enabled = isEnabledHandler) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    val startBtnString = stringResource(R.string.start_date_field)
    var startBtnText by remember {
        mutableStateOf(startBtnString)
    }
    var startDate by remember {
        mutableStateOf(LocalDate.MIN)
    }

    val endBtnString = stringResource(R.string.end_date_field)
    var endBtnText by remember {
        mutableStateOf(endBtnString)
    }
    var endDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val isEnabledEndDateBtn = !startBtnText.contains(" ")
    val isEnabledConfirmBtn = !startBtnText.contains(" ") && !endBtnText.contains(" ")

    Column(
        modifier = Modifier.fillMaxHeight(0.4F),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        Image(
            painter = painterResource(id = R.drawable.ic_horizontal_line),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))

        Text(text = stringResource(id = R.string.select_days))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))

        Row(
            modifier = Modifier.fillMaxWidth(0.8F),
            verticalAlignment = Alignment.CenterVertically) {
            DateSelectionBtn(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2))
                    .weight(1F),
                onClick = { dialogStartDateState.show() },
                label = startBtnText
            )

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_horizontal_black),
                contentDescription = null
            )

            DateSelectionBtn(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2))
                    .weight(1F)
                    .alpha(if(isEnabledEndDateBtn) 1F else 0.4F),
                onClick = { dialogEndDateState.show() },
                label = endBtnText,
                enabled = isEnabledEndDateBtn
            )
        }
        Spacer(modifier = Modifier.weight(1F))

        Button(
            modifier = Modifier.fillMaxWidth(0.6F).alpha(
                if(isEnabledConfirmBtn) 1F else 0.4F
            ),
            onClick = {
                val startDateInSec = TimeUtility.getTimeInMilliseconds(startDate)
                val endDateInSec = TimeUtility.getTimeInMilliseconds(endDate)

                onDaysSelected(Pair(startDateInSec, endDateInSec))
                scope.launch {
                    bottomSheetState.hide()
                }
            },
            enabled = isEnabledConfirmBtn,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            )

        ) {
            Text(
                text = stringResource(R.string.show_selected_days),
                style = MaterialTheme.typography.displaySmall
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))
    }

    StartDatePicker(
        dialogState = dialogStartDateState,
        endDate = endDate,
        onDatePicked = { startDate = it },
        onChangeFieldText = { startBtnText = it })

    EndDatePicker(
        dialogState = dialogEndDateState,
        startDate = startDate,
        onDatePicked = { endDate = it },
        onChangeFieldText = { endBtnText = it })

}