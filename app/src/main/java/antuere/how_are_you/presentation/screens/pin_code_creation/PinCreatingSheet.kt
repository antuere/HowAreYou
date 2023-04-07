package antuere.how_are_you.presentation.screens.pin_code_creation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationSideEffect
import antuere.how_are_you.presentation.screens.pin_code_creation.ui_compose.PinCreatingSheetState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun PinCreatingSheet(
    onHandleResult: (Boolean) -> Unit,
    viewModel: PinCreatingSheetViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : composed pin code creating")
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PinCreationSideEffect.PinCreated -> {
                onHandleResult(true)
            }
        }
    }

    PinCreatingSheetState(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
    )
}
