package antuere.how_are_you.presentation.base.ui_compose_components.text_field

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import antuere.how_are_you.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeHolder: String? = null,
    singleLine: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    @StringRes toastTextId: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null
) {
    val context = LocalContext.current
    val toastText = stringResource(toastTextId ?: R.string.too_many_chars_default)

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            } else {
                Toast.makeText(
                    context,
                    toastText,
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        placeholder = {
            placeHolder?.let {
                Text(text = it)
            }
        },
        textStyle = MaterialTheme.typography.displaySmall.copy(
            color = MaterialTheme.colorScheme.onSecondary
        ),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.displaySmall,
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon
    )
}
