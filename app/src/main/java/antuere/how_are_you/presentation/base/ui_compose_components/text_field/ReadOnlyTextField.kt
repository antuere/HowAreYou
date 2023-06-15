package antuere.how_are_you.presentation.base.ui_compose_components.text_field

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ReadOnlyTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.secondary,
        unfocusedTextColor = MaterialTheme.colorScheme.outlineVariant,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
    ),
    shape: Shape = MaterialTheme.shapes.large,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        readOnly = true,
        modifier = modifier,
        value = value,
        onValueChange = {},
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Unspecified,
                    fontWeight = FontWeight.Medium
                ),
            )
        },
        colors = colors,
        shape = shape,
        singleLine = singleLine,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        textStyle = LocalTextStyle.current.copy(color = Color.Unspecified)
    )
}