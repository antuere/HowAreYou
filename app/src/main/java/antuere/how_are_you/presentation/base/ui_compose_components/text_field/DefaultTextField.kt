package antuere.how_are_you.presentation.base.ui_compose_components.text_field

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import antuere.how_are_you.R
import antuere.how_are_you.util.isKeyboardVisible
import timber.log.Timber

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    label: String,
    placeHolder: String? = null,
    singleLine: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = 25,
    shape: Shape = MaterialTheme.shapes.large,
    @StringRes toastTextId: Int? = null,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        errorContainerColor = MaterialTheme.colorScheme.background,
        errorTextColor = MaterialTheme.colorScheme.error,
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    Timber.i("Acc settings check: enter in DEFAULT textfield")

    val context = LocalContext.current
    val toastText = stringResource(toastTextId ?: R.string.too_many_chars_default)
    val focusManager = LocalFocusManager.current
    val isVisibleKeyboard by isKeyboardVisible()

    LaunchedEffect(isVisibleKeyboard) {
        if (!isVisibleKeyboard) {
            focusManager.clearFocus()
        }
    }

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
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage ?: stringResource(id = R.string.default_error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = colors,
        shape = shape,
        keyboardOptions = keyboardOptions.copy(capitalization = KeyboardCapitalization.Sentences),
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        maxLines = maxLines,
        enabled = enabled,
        isError = isError
    )
}
