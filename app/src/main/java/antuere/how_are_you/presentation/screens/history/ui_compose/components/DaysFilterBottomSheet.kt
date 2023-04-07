package antuere.how_are_you.presentation.screens.history.ui_compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker.EndDatePicker
import antuere.how_are_you.presentation.screens.history.ui_compose.components.date_picker.StartDatePicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import timber.log.Timber
import java.time.LocalDate

@Composable
fun DaysFilterBottomSheet(
    onDaysSelected: (LocalDate, LocalDate) -> Unit,
) {
    Timber.i("MVI error test : composed in daysFilter")

    val dialogStartDateState = rememberMaterialDialogState()
    val dialogEndDateState = rememberMaterialDialogState()

    val startBtnString = stringResource(R.string.start_date_field)
    var startBtnText by remember { mutableStateOf(startBtnString) }
    var startDate by remember { mutableStateOf(LocalDate.MIN) }

    val endBtnString = stringResource(R.string.end_date_field)
    var endBtnText by remember { mutableStateOf(endBtnString) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }

    val isEnabledEndDateBtn by remember {
        derivedStateOf {
            !startBtnText.contains(" ")
        }
    }
    val isEnabledConfirmBtn by remember {
        derivedStateOf {
            !startBtnText.contains(" ") && !endBtnText.contains(" ")
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight(0.4F),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        Text(text = stringResource(id = R.string.select_days))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))

        Row(
            modifier = Modifier.fillMaxWidth(0.9F),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    .alpha(if (isEnabledEndDateBtn) 1F else 0.4F),
                onClick = { dialogEndDateState.show() },
                label = endBtnText,
                enabled = isEnabledEndDateBtn
            )
        }
        Spacer(modifier = Modifier.weight(1F))

        Button(
            modifier = Modifier
                .fillMaxWidth(0.6F)
                .alpha(if (isEnabledConfirmBtn) 1F else 0.4F),
            onClick = {
                onDaysSelected(startDate, endDate)
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
