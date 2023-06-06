package antuere.how_are_you.presentation.screens.sign_in_with_email.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultTextButton
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.PopUpProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.presentation.screens.sign_in_with_email.state.SignInEmailIntent
import antuere.how_are_you.presentation.screens.sign_in_with_email.state.SignInEmailState
import antuere.how_are_you.util.extensions.bringIntoViewForFocused
import antuere.how_are_you.util.extensions.paddingTopBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignInEmailScreenState(
    viewState: () -> SignInEmailState,
    onIntent: (SignInEmailIntent) -> Unit,
    focusManager : FocusManager
) {
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }

    if (viewState().isShowProgressIndicator) {
        PopUpProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconApp(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_normal_5)))
            Spacer(modifier = Modifier.weight(1F))

            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState().email,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = { onIntent(SignInEmailIntent.EmailChanged(it)) }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth()
                    .bringIntoViewForFocused(
                        bringIntoViewRequester = bringIntoViewRequester,
                        scope = scope
                    ),
                labelId = R.string.password,
                value = viewState().password,
                keyboardActions = KeyboardActions(onDone = {
                    onIntent(SignInEmailIntent.SignInBtnClicked)
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_large_2),
                        vertical = dimensionResource(id = R.dimen.padding_normal_1),
                    )
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester),
                labelId = R.string.sign_in,
                onClick = {
                    onIntent(SignInEmailIntent.SignInBtnClicked)
                }
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultTextButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_normal_2)),
                labelId = R.string.don_have_acc,
                onClick = { onIntent(SignInEmailIntent.SignUpBtnClicked) }
            )
        }
    }
}