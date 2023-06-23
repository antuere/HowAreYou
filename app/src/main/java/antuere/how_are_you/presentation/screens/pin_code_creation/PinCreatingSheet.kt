package antuere.how_are_you.presentation.screens.pin_code_creation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationSideEffect
import antuere.how_are_you.presentation.screens.pin_code_creation.ui_compose.PinCreatingSheetContent
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun PinCreatingSheet(
    onHandleResult: (Boolean) -> Unit,
    viewModel: PinCreatingSheetViewModel = hiltViewModel(),
) {
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PinCreationSideEffect.PinCreated -> {
                onHandleResult(true)
            }
        }
    }

    PinCreatingSheetContent(
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
    )
}
