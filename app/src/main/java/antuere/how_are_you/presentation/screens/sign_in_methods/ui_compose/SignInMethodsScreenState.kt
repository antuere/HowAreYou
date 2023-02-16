package antuere.how_are_you.presentation.screens.sign_in_methods.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.ButtonWithIcon
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsIntent
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun SignInMethodsScreenState(
    viewState: () -> SignInMethodsState,
    onIntent: (SignInMethodsIntent) -> Unit,
) {
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