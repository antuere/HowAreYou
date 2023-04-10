package antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import antuere.domain.util.TimeFormat
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDateRangePicker(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    dateSelected: (SelectedDates) -> Unit,
) {
    val state = rememberDateRangePickerState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(0.9F),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Filled.Close, contentDescription = "Close picker")
                    }
                    TextButton(
                        onClick = {
                            dateSelected(
                                SelectedDates(
                                    startInMillis = state.selectedStartDateMillis!!,
                                    endInMillis = state.selectedEndDateMillis!!
                                )
                            )
                            onDismissRequest()
                        },
                        enabled = state.selectedEndDateMillis != null
                    ) {
                        Text(
                            text = stringResource(R.string.datePicker_confirm),
                            color = if (state.selectedEndDateMillis != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }

                DateRangePicker(
                    state = state,
                    modifier = Modifier.weight(1f),
                    showModeToggle = false,
                    dateValidator = { it <= System.currentTimeMillis() },
                    headline = { CustomDateRangePickerHeadline(state = state) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePickerHeadline(
    state: DateRangePickerState,
    modifier: Modifier = Modifier,
) {
    val startDateText = stringResource(R.string.start_date_field)
    val endDateText = stringResource(R.string.end_date_field)

    CustomDatePickerHeadline(
        state = state,
        modifier = modifier,
        startDateText = startDateText,
        endDateText = endDateText,
        startDatePlaceholder = {
            Text(
                text = startDateText,
                style = MaterialTheme.typography.titleMedium
            )
        },
        endDatePlaceholder = {
            Text(
                text = endDateText,
                style = MaterialTheme.typography.titleMedium
            )
        },
        datesDelimiter = { Text(text = " - ") },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomDatePickerHeadline(
    state: DateRangePickerState,
    modifier: Modifier,
    startDateText: String,
    endDateText: String,
    startDatePlaceholder: @Composable () -> Unit,
    endDatePlaceholder: @Composable () -> Unit,
    datesDelimiter: @Composable () -> Unit,
) {
    val formatterStartDate: String? = if (state.selectedStartDateMillis == null) {
        null
    } else {
        TimeUtility.formatDate(
            date = Date(state.selectedStartDateMillis!!),
            format = TimeFormat.MonthNamed
        )
    }

    val formatterEndDate: String? = if (state.selectedEndDateMillis == null) {
        null
    } else {
        TimeUtility.formatDate(
            date = Date(state.selectedEndDateMillis!!),
            format = TimeFormat.MonthNamed
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clearAndSetSemantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = "$startDateText, $endDateText"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (formatterStartDate != null) {
            Text(text = formatterStartDate, style = MaterialTheme.typography.bodyLarge)
        } else {
            startDatePlaceholder()
        }
        datesDelimiter()
        if (formatterEndDate != null) {
            Text(text = formatterEndDate, style = MaterialTheme.typography.bodyLarge)
        } else {
            endDatePlaceholder()
        }
    }
}

