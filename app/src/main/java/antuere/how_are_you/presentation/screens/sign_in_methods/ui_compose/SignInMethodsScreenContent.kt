package antuere.how_are_you.presentation.screens.sign_in_methods.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.ButtonWithIcon
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.PopUpProgressIndicator
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsIntent
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun SignInMethodsScreenContent(
    viewState: () -> SignInMethodsState,
    onIntent: (SignInMethodsIntent) -> Unit,
) {
    if (viewState().isLoading) {
        PopUpProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonWithIcon(
                modifier = Modifier.fillMaxWidth(0.7F),
                onClick = { onIntent(SignInMethodsIntent.EmailMethodClicked) },
                labelId = viewState().emailMethod.nameId,
                iconId = viewState().emailMethod.iconId
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

            ButtonWithIcon(
                modifier = Modifier.fillMaxWidth(0.7F),
                onClick = { onIntent(SignInMethodsIntent.GoogleMethodClicked) },
                labelId = viewState().googleMethod.nameId,
                iconId = viewState().googleMethod.iconId,
            )
        }
    }
}