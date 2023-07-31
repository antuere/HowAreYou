package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R

@Composable
fun SignInAdviceRow(
    modifier: Modifier = Modifier,
    onClickInfo: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            text = stringResource(id = R.string.sign_in_advice)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))

        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onClickInfo
        ) {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "Info button",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}