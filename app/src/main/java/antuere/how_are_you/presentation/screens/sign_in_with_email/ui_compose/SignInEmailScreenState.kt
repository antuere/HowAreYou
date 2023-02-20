package antuere.how_are_you.presentation.screens.sign_in_with_email.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultTextButton
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.presentation.screens.sign_in_with_email.state.SignInEmailIntent
import antuere.how_are_you.presentation.screens.sign_in_with_email.state.SignInEmailState
import antuere.how_are_you.util.extensions.paddingTopBar
import timber.log.Timber

@Composable
fun SignInEmailScreenState(
    viewState: () -> SignInEmailState,
    onIntent: (SignInEmailIntent) -> Unit,
) {
    Timber.i("MVI error test : enter in signInEmailScreen")

    if (viewState().isShowProgressIndicator) {
        FullScreenProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Timber.i("MVI error test : enter in column")

            IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState().email,
                onValueChange = { onIntent(SignInEmailIntent.EmailChanged(it)) }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.password,
                value = viewState().password,
                onValueChange = { onIntent(SignInEmailIntent.PasswordChanged(it)) }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

            DefaultTextButton(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_normal_3))
                    .align(Alignment.End),
                labelId = R.string.reset_password_hint,
                onClick = { onIntent(SignInEmailIntent.ResetPassBtnClicked) }
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.sign_in,
                onClick = { onIntent(SignInEmailIntent.SignInBtnClicked) }
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultTextButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.don_have_acc,
                onClick = { onIntent(SignInEmailIntent.SignUpBtnClicked) }
            )
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}