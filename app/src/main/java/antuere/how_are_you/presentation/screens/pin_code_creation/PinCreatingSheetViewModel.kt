package antuere.how_are_you.presentation.screens.pin_code_creation

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationIntent
import antuere.how_are_you.presentation.screens.pin_code_creation.state.PinCreationSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PinCreatingSheetViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<PinCirclesState, PinCreationSideEffect, PinCreationIntent>() {

    override val container: Container<PinCirclesState, PinCreationSideEffect> =
        container(PinCirclesState.NONE)

    private var numbers = mutableListOf<String>()
    private var currentPinCode = Constants.PIN_NOT_SET

    override fun onIntent(intent: PinCreationIntent) {
        when (intent) {
            is PinCreationIntent.NumberClicked -> {
                if (intent.number.length != 1) {
                    throw IllegalArgumentException("Invalid number: ${intent.number}")
                }

                numbers.add(intent.number)
                checkPassword(numbers)
            }
            PinCreationIntent.PinStateReset -> {
                updateState { PinCirclesState.NONE }
                currentPinCode = Constants.PIN_NOT_SET
                numbers.clear()
            }
        }
    }

    private fun checkPassword(list: List<String>) {
        when (list.size) {
            1 -> {
                updateState { PinCirclesState.FIRST }
            }
            2 -> {
                updateState { PinCirclesState.SECOND }
            }
            3 -> {
                updateState { PinCirclesState.THIRD }
            }
            4 -> {
                updateState { PinCirclesState.FOURTH }
                currentPinCode = numbers[0] + numbers[1] + numbers[2] + numbers[3]
                pinCodeCreated()
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }


    private fun pinCodeCreated() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.savePinCode(currentPinCode)
            delay(100)

            sideEffect(PinCreationSideEffect.PinCreated)

            updateState { PinCirclesState.NONE }
            currentPinCode = Constants.PIN_NOT_SET
            numbers.clear()
        }
    }
}