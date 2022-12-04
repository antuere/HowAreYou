package com.example.zeroapp.presentation.settings.ui_compose

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zeroapp.R

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    @StringRes descriptionId: Int,
    isChecked: Boolean,
    checkChanged: (Boolean) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = stringResource(id = titleId),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
            )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = stringResource(id = descriptionId),
                fontSize = dimensionResource(id = R.dimen.textSize_small_0).value.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = dimensionResource(id = R.dimen.textLine_height_default).value.sp,
            )
        }
        Switch(
            modifier = Modifier
                .padding(start = 8.dp),
            checked = isChecked,
            onCheckedChange = checkChanged,
            colors = SwitchDefaults.colors(uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary)
        )
    }
}