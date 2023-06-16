package antuere.how_are_you.presentation.screens.account_settings.ui_compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.ReadOnlyTextField
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsIntent
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun MainContentAccountSettings(
    nickname: String,
    email: String,
    onIntent: (AccountSettingsIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_normal_1))
    ) {
        ReadOnlyTextField(
            modifier = Modifier.fillMaxWidth(0.9F),
            label = stringResource(id = R.string.nickname),
            value = nickname
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        ReadOnlyTextField(
            modifier = Modifier.fillMaxWidth(0.9F),
            label = stringResource(id = R.string.email),
            value = email
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Button(
            onClick = { onIntent(AccountSettingsIntent.DeleteDataBtnClicked) },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = dimensionResource(id = R.dimen.default_elevation),
                pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
            ),
            modifier = Modifier.fillMaxWidth(0.8F)
        ) {
            Text(
                text = stringResource(id = R.string.delete_data),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Button(
            onClick = { onIntent(AccountSettingsIntent.DeleteAccountBtnClicked) },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = dimensionResource(id = R.dimen.default_elevation),
                pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
            ),
            modifier = Modifier.fillMaxWidth(0.8F)
        ) {
            Text(
                text = stringResource(id = R.string.delete_account),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Button(
            onClick = { onIntent(AccountSettingsIntent.SignOutBtnClicked) },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = dimensionResource(id = R.dimen.default_elevation),
                pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
            ),
            modifier = Modifier.fillMaxWidth(0.8F)
        ) {
            Text(
                text = stringResource(id = R.string.sign_out),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}