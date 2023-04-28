package antuere.how_are_you.presentation.screens.add_day.ui_compose.components

import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField

@Composable
fun DayDescriptionField(
    modifier: Modifier,
    value: () -> String,
    onValueChange: (String) -> Unit,
    isEnabled: () -> Boolean,
) {
    DefaultTextField(
        modifier = modifier,
        label = stringResource(id = R.string.desc_you_day),
        value = value(),
        onValueChange = onValueChange,
        maxLength = 1000,
        shape = ShapeDefaults.Small,
        enabled = isEnabled()
    )
}