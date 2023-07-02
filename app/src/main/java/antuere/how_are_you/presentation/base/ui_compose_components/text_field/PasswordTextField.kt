package antuere.how_are_you.presentation.base.ui_compose_components.text_field

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    @StringRes labelId: Int,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    DefaultTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = stringResource(id = labelId),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Password),
        singleLine = true,
        isError = isError,
        errorMessage = errorMessage,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val passwordIcon = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = passwordVisible.not() }) {
                Icon(
                    imageVector = if (!isError) passwordIcon else Icons.Outlined.Error,
                    contentDescription = description,
                    tint = if (!isError) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error
                )
            }
        }
    )
}