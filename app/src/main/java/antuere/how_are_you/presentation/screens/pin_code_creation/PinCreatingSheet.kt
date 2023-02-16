package antuere.how_are_you.presentation.screens.pin_code_creation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationIntent
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationSideEffect
import antuere.how_are_you.presentation.screens.pin_code_creation.ui_compose.PinCreatingSheetState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PinCodeCreating(
    bottomSheetState: ModalBottomSheetState,
    onHandleResult: (Boolean) -> Unit,
    viewModel: PinCreatingSheetViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : composed pin code creating")
    val viewState by viewModel.collectAsState()

    val isSheetStartsHiding by remember {
        derivedStateOf {
            bottomSheetState.progress.from == ModalBottomSheetValue.Expanded
                    &&
                    bottomSheetState.progress.to == ModalBottomSheetValue.Hidden
                    && bottomSheetState.progress.fraction >= 0.85f
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PinCreationSideEffect.PinCreated -> {
                onHandleResult(true)
            }
        }
    }

    LaunchedEffect(isSheetStartsHiding) {
        Timber.i("MVI error test : error , isSheetStartsHiding : $isSheetStartsHiding")
        if (isSheetStartsHiding) {
            if (viewState != PinCirclesState.FOURTH) {
                Timber.i("MVI error test : error, handle result false")
                onHandleResult(false)
            }
            PinCreationIntent.PinStateReset.run(viewModel::onIntent)
        }
    }

    PinCreatingSheetState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
    )
}
