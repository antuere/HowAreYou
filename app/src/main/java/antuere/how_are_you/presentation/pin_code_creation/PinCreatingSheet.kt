package antuere.how_are_you.presentation.pin_code_creation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.NumericKeyPad
import antuere.how_are_you.presentation.base.ui_compose_components.pin_code.PinCirclesIndicates
import antuere.how_are_you.presentation.pin_code_creation.state.PinCreationIntent
import antuere.how_are_you.presentation.pin_code_creation.state.PinCreationSideEffect
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun PinCodeCreating(
    hideBottomSheet: () -> Unit,
    isSheetStartsHiding: Boolean,
    onHandleResult: (Boolean) -> Unit,
    viewModel: PinCreatingSheetViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : composed pin code creating")

    val viewState by viewModel.collectAsState()

    val onClickNumber: (String) -> Unit = remember {
        { PinCreationIntent.NumberClicked(it).run(viewModel::onIntent) }
    }

    val resetPinState: () -> Unit = remember {
        { PinCreationIntent.PinStateReset.run(viewModel::onIntent) }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PinCreationSideEffect.PinCreated -> {
                onHandleResult(true)
                hideBottomSheet()
            }
        }
    }

    LaunchedEffect(isSheetStartsHiding) {
        if (isSheetStartsHiding) {
            if (viewState != PinCirclesState.FOURTH) {
                onHandleResult(false)
            }
            resetPinState()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Image(
            painter = painterResource(id = R.drawable.ic_horizontal_line),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        Text(text = stringResource(id = R.string.create_a_pin_code))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

        PinCirclesIndicates(viewState)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

        NumericKeyPad(
            onClick = onClickNumber,
            onClickClear = resetPinState
        )

        Spacer(
            modifier =
            Modifier.height(dimensionResource(id = R.dimen.spacer_height_7))
        )
    }
}
