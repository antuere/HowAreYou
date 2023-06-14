package antuere.how_are_you.presentation.screens.account_settings.ui_compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.window.DialogProperties
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.DefaultDialogFlowRow
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsState
import antuere.how_are_you.util.extensions.fixedSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReauthWithPasswordDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onPasswordChanged: (String) -> Unit,
    onClickDeleteAcc: () -> Unit,
    viewState: () -> AccountSettingsState,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.9F),
            color = MaterialTheme.colorScheme.background,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_normal_2)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reauth),
                    contentDescription = "Password reauth",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

                Text(
                    text = stringResource(R.string.reauth_password_title),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24f.fixedSize)
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

                Text(
                    stringResource(R.string.reauth_password_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_normal_2)))

                PasswordTextField(
                    labelId = R.string.password,
                    value = viewState().userEnteredPassword,
                    onValueChange = onPasswordChanged,
                    isError = viewState().isShowErrorInTextField,
                    errorMessage = viewState().errorMessage.asString(),
                    keyboardActions = KeyboardActions(onDone = {
                        onClickDeleteAcc()
                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_normal_2)))

                Box(modifier = Modifier.align(Alignment.End)) {
                    DefaultDialogFlowRow {
                        TextButton(onClick = onDismissRequest) {
                            Text(
                                stringResource(id = R.string.dialog_no),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        TextButton(onClick = onClickDeleteAcc) {
                            Text(
                                stringResource(id = R.string.reauth_password_positive),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}