package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent

@Composable
fun DayEditTextField(
    modifier: Modifier = Modifier,
    value: () -> String,
    onIntent: (DetailIntent) -> Unit,
) {
    DefaultTextField(
        modifier = modifier,
        label = stringResource(id = R.string.desc_you_day),
        value = value(),
        onValueChange = { onIntent(DetailIntent.DayDescChanged(it)) },
        maxLength = 1000,
    )
}