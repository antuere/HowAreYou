package antuere.how_are_you.presentation.sign_up_with_email.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.presentation.sign_up_with_email.state.SignUpEmailIntent
import antuere.how_are_you.presentation.sign_up_with_email.state.SignUpEmailState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun SignUpEmailScreenState(
    viewState: () -> SignUpEmailState,
    onIntent: (SignUpEmailIntent) -> Unit,
) {

    if (viewState().isShowProgressIndicator) {
        FullScreenProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            DefaultTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState().nickName,
                onValueChange = { onIntent(SignUpEmailIntent.NicknameChanged(it)) },
                label = stringResource(id = R.string.nickname),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState().email,
                onValueChange = { onIntent(SignUpEmailIntent.EmailChanged(it)) },
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.password,
                value = viewState().password,
                onValueChange = { onIntent(SignUpEmailIntent.PasswordChanged(it)) },
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.confirm_password,
                value = viewState().confirmPassword,
                onValueChange = { onIntent(SignUpEmailIntent.ConfirmPasswordChanged(it)) },
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.sign_up,
                onClick = { onIntent(SignUpEmailIntent.SignInBtnClicked) },
            )
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}