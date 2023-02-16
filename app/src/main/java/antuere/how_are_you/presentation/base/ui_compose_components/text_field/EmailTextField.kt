package antuere.how_are_you.presentation.base.ui_compose_components.text_field

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import antuere.how_are_you.R

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    DefaultTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = stringResource(id = R.string.email),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}