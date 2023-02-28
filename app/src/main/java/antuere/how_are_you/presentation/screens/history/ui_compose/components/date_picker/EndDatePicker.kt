package antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun EndDatePicker(
    dialogState: MaterialDialogState,
    startDate: LocalDate,
    onDatePicked: (LocalDate) -> Unit,
    onChangeFieldText: (String) -> Unit
) {
    MaterialDialog(
        elevation = 0.dp,
        dialogState = dialogState,
        shape = ShapeDefaults.ExtraLarge,
        buttons = {
            positiveButton(res = R.string.ok, textStyle = MaterialTheme.typography.labelSmall)
            negativeButton(res = R.string.cancel, textStyle = MaterialTheme.typography.labelSmall)
        }
    ) {
        datepicker(
            title = stringResource(id = R.string.date_picker_title),
            allowedDateValidator = {
                it <= LocalDate.now() && it > startDate
            },
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primary,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.tertiary,
                dateActiveTextColor = MaterialTheme.colorScheme.onPrimary
            )
        ) { selectedDate ->
            onDatePicked(selectedDate)
            onChangeFieldText(TimeUtility.formatLocalDate(selectedDate))
        }
    }
}