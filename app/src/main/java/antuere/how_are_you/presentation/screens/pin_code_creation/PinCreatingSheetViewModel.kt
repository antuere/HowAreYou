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

    private var num1: String? = null
    private var num2: String? = null
    private var num3: String? = null
    private var num4: String? = null
    private var currentNumbers = mutableListOf<String>()

    private var currentPinCode = Constants.PIN_NOT_SET

    override fun onIntent(intent: PinCreationIntent)  {
        when (intent) {
            is PinCreationIntent.NumberClicked -> {
                if (intent.number.length != 1) {
                    throw IllegalArgumentException("Invalid number: ${intent.number}")
                }

                currentNumbers.add(intent.number)
                checkPassword(currentNumbers)
            }
            PinCreationIntent.PinStateReset -> {
                updateState { PinCirclesState.NONE }
                currentPinCode = Constants.PIN_NOT_SET
                currentNumbers.clear()
            }
        }
    }

    private fun checkPassword(list: List<String>)  {
        when (list.size) {
            1 -> {
                num1 = list[0]
                updateState { PinCirclesState.FIRST }
            }
            2 -> {
                num2 = list[1]
                updateState { PinCirclesState.SECOND }
            }
            3 -> {
                num3 = list[2]
                updateState { PinCirclesState.THIRD }
            }
            4 -> {
                num4 = list[3]
                updateState { PinCirclesState.FOURTH }
                currentPinCode = num1 + num2 + num3 + num4
                pinCodeCreated()
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }


    private fun pinCodeCreated()  {
        viewModelScope.launch(Dispatchers.IO) {
            savePinCode(currentPinCode)
            delay(100)

            sideEffect(PinCreationSideEffect.PinCreated)
        }
    }

    private fun savePinCode(pinCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.savePinCode(pinCode)
        }
    }
}