package antuere.how_are_you.presentation.screens.reset_password.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.PopUpProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.screens.reset_password.state.ResetPasswordIntent
import antuere.how_are_you.presentation.screens.reset_password.state.ResetPasswordState
import antuere.how_are_you.presentation.screens.reset_password.ui_compose.components.ResetPasswordTitleText
import antuere.how_are_you.util.extensions.bringIntoViewForFocused
import antuere.how_are_you.util.extensions.paddingTopBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResetPasswordScreenContent(
    viewState: () -> ResetPasswordState,
    onIntent: (ResetPasswordIntent) -> Unit,
) {
    val bringIntoViewRequester = BringIntoViewRequester()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
            .navigationBarsPadding()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (viewState().isShowProgressIndicator) {
            PopUpProgressIndicator()
        } else {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
            ResetPasswordTitleText()

            Spacer(modifier = Modifier.weight(1F))
            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth()
                    .bringIntoViewForFocused(
                        bringIntoViewRequester = bringIntoViewRequester,
                        scope = scope
                    ),
                value = viewState().email,
                keyboardActions = KeyboardActions(onDone = {
                    onIntent(ResetPasswordIntent.ResetBtnClicked)
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                onValueChange = { onIntent(ResetPasswordIntent.EmailChanged(it)) }
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultButton(
                modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
                labelId = R.string.reset_password,
                onClick = {
                    onIntent(ResetPasswordIntent.ResetBtnClicked)
                }
            )
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}