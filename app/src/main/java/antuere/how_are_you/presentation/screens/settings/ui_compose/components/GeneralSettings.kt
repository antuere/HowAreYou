package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.divider.DefaultDivider
import antuere.how_are_you.presentation.base.ui_compose_components.settings.SettingItem

@Composable
fun GeneralSettings(
    isCheckedWorriedDialog: Boolean,
    onCheckedChangeWorriedDialog: (Boolean) -> Unit,
    onClickDayCustomization: () -> Unit,
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
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        SettingItem(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal_3),
                end = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            titleId = R.string.settings_day_customization,
            iconId = R.drawable.ic_palette,
            onClick = onClickDayCustomization
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        SettingItemWithSwitch(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_normal_3),
                end = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            titleId = R.string.show_worried_dialog_title,
            descriptionId = R.string.show_worried_dialog_desc,
            isChecked = isCheckedWorriedDialog,
            onCheckedChange = onCheckedChangeWorriedDialog,
            iconId = R.drawable.ic_settings_dialog
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        DefaultDivider(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_normal_2)
                )
        )

    }
}