package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int,
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(size = 24.dp),
            painter = painterResource(id = iconId),
            contentDescription = "setting icon",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_1)))

        Text(
            modifier = Modifier.weight(1F),
            text = stringResource(id = titleId),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            textAlign = TextAlign.Start,
        )
    }
}