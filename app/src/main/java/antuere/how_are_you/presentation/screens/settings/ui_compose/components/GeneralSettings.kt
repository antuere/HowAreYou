package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R

@Composable
fun GeneralSettings(
    isCheckedWorriedDialog: Boolean,
    onCheckedChangeWorriedDialog: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Text(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            text = stringResource(id = R.string.settings_general_text),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        SettingItem(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal_4),
                end = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            titleId = R.string.show_worried_dialog_title,
            descriptionId = R.string.show_worried_dialog_desc,
            isChecked = isCheckedWorriedDialog,
            onCheckedChange = onCheckedChangeWorriedDialog
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Divider(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_normal_2)
                )
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSecondary
        )

    }
}