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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
            .padding(dimensionResource(id = R.dimen.padding_normal_1)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.settings_acc_info),
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        ReadOnlyTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2)),
            label = stringResource(id = R.string.nickname),
            value = nickname
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        ReadOnlyTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2)),
            label = stringResource(id = R.string.email),
            value = email
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.settings_acc_manage),
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        Button(
            onClick = { onIntent(AccountSettingsIntent.DeleteDataBtnClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2))
        ) {
            Text(
                text = stringResource(id = R.string.delete_data),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Button(
            onClick = { onIntent(AccountSettingsIntent.SignOutBtnClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2))
        ) {
            Text(
                text = stringResource(id = R.string.sign_out),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = { onIntent(AccountSettingsIntent.DeleteAccountBtnClicked) }) {
            Text(
                text = stringResource(id = R.string.delete_account),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}