package antuere.how_are_you.presentation.base.ui_compose_components.buttons

import androidx.annotation.StringRes
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    @StringRes labelId: Int,
    onClick: () -> Unit
) {
    ElevatedButton(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = dimensionResource(id = R.dimen.default_elevation),
            pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
        ),
        onClick = { onClick() }) {
        Text(
            text = stringResource(id = labelId),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}