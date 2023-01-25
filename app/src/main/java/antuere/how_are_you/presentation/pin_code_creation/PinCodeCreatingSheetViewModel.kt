package antuere.how_are_you.presentation.pin_code_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
import antuere.how_are_you.presentation.pin_code_creation.state.PinCodeCreationSideEffect
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
class PinCodeCreatingSheetViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ContainerHost<PinCodeCirclesState, PinCodeCreationSideEffect>, ViewModel() {

    override val container: Container<PinCodeCirclesState, PinCodeCreationSideEffect> =
        container(PinCodeCirclesState.NONE)

    private var userPinCode = Constants.PIN_NOT_SET

    private var num1: String? = null
    private var num2: String? = null
    private var num3: String? = null
    private var num4: String? = null

    private var currentNumbers = mutableListOf<String>()

    fun onClickNumber(value: String) {
        if (value.length != 1) {
            throw IllegalArgumentException("Invalid number: $value")
        }

        currentNumbers.add(value)
        checkPassword(currentNumbers)
    }

    fun resetAllPinCodeStates() = intent {
        reduce {
            PinCodeCirclesState.NONE
        }
        userPinCode = Constants.PIN_NOT_SET
        currentNumbers.clear()
    }

    private fun checkPassword(list: List<String>) = intent {
        when (list.size) {
            1 -> {
                num1 = list[0]
                reduce {
                    PinCodeCirclesState.FIRST
                }
            }
            2 -> {
                num2 = list[1]
                reduce {
                    PinCodeCirclesState.SECOND
                }
            }
            3 -> {
                num3 = list[2]
                reduce {
                    PinCodeCirclesState.THIRD
                }
            }
            4 -> {
                num4 = list[3]
                reduce {
                    PinCodeCirclesState.FOURTH
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

            postSideEffect(PinCodeCreationSideEffect.PinCreated)
        }
    }

    private fun savePinCode(pinCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.savePinCode(pinCode)
        }
    }
}