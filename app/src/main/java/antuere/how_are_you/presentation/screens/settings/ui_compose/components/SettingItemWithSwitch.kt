package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R

@Composable
fun SettingItemWithSwitch(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    @StringRes descriptionId: Int,
    @DrawableRes iconId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(size = 24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "setting icon",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_1)))

        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = stringResource(id = titleId),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = descriptionId),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8F),
                    fontSize = dimensionResource(id = R.dimen.textSize_small_0).value.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Switch(
            modifier = Modifier.padding(start = 12.dp),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary)
        )
    }
}