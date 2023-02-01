package antuere.how_are_you.presentation.base.ui_compose_components.buttons

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    @StringRes labelId: Int,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Text(
            text = stringResource(id = labelId),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}