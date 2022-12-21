package com.example.zeroapp.presentation.history.ui_compose.date_picker

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun StartDatePicker(
    dialogState: MaterialDialogState,
    endDate: LocalDate,
    onDatePicked: (LocalDate) -> Unit,
    onChangeFieldText: (String) -> Unit
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(text = stringResource(R.string.ok))
            negativeButton(text = stringResource(R.string.cancel))
        }
    ) {
        datepicker(
            title = stringResource(id = R.string.date_picker_title),
            allowedDateValidator = {
                it <= LocalDate.now() && it < endDate
            }
        ) { selectedDate ->
            onDatePicked(selectedDate)
            onChangeFieldText(TimeUtility.formatLocalDate(selectedDate))
        }
    }
}