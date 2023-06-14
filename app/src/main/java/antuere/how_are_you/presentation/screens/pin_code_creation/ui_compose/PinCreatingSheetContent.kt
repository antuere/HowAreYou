package antuere.how_are_you.presentation.screens.pin_code_creation.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.NumericKeypad
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.PinCirclesIndicatesWrapper
import antuere.how_are_you.presentation.screens.pin_code_creation.PinCirclesState
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationIntent

@Composable
fun PinCreatingSheetContent(
    viewState: () -> PinCirclesState,
    onIntent: (PinCreationIntent) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        Text(text = stringResource(id = R.string.create_a_pin_code))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        PinCirclesIndicatesWrapper(viewState)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

        NumericKeypad(
            onClick = { onIntent(PinCreationIntent.NumberClicked(it)) },
            onClickClear = { onIntent(PinCreationIntent.PinStateReset) },
        )

        Spacer(
            modifier =
            Modifier.height(dimensionResource(id = R.dimen.spacer_height_7))
        )
    }
}