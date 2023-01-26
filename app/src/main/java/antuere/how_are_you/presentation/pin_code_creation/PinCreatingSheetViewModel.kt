package antuere.how_are_you.presentation.pin_code_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
import antuere.how_are_you.presentation.pin_code_creation.state.PinCreationIntent
import antuere.how_are_you.presentation.pin_code_creation.state.PinCreationSideEffect
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class PinCreatingSheetViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ContainerHostPlus<PinCirclesState, PinCreationSideEffect, PinCreationIntent>, ViewModel() {

    override val container: Container<PinCirclesState, PinCreationSideEffect> =
        container(PinCirclesState.NONE)

    private var userPinCode = Constants.PIN_NOT_SET

    private var num1: String? = null
    private var num2: String? = null
    private var num3: String? = null
    private var num4: String? = null

    private var currentNumbers = mutableListOf<String>()

    override fun onIntent(intent: PinCreationIntent) = intent {
        when (intent) {
            is PinCreationIntent.NumberClicked -> {
                if (intent.number.length != 1) {
                    throw IllegalArgumentException("Invalid number: ${intent.number}")
                }

                currentNumbers.add(intent.number)
                checkPassword(currentNumbers)
            }
            PinCreationIntent.PinStateReset -> {
                reduce {
                    PinCirclesState.NONE
                }
                userPinCode = Constants.PIN_NOT_SET
                currentNumbers.clear()
            }
        }
    }

    private fun checkPassword(list: List<String>) = intent {
        when (list.size) {
            1 -> {
                num1 = list[0]
                reduce {
                    PinCirclesState.FIRST
                }
            }
            2 -> {
                num2 = list[1]
                reduce {
                    PinCirclesState.SECOND
                }
            }
            3 -> {
                num3 = list[2]
                reduce {
                    PinCirclesState.THIRD
                }
            }
            4 -> {
                num4 = list[3]
                reduce {
                    PinCirclesState.FOURTH
                }
                userPinCode = num1 + num2 + num3 + num4
                pinCodeCreated()
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }


    private fun pinCodeCreated() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            savePinCode(userPinCode)
            delay(100)

            postSideEffect(PinCreationSideEffect.PinCreated)
        }
    }

    private fun savePinCode(pinCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.savePinCode(pinCode)
        }
    }
}