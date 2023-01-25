package antuere.how_are_you.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.usecases.AddDayUseCase
import antuere.how_are_you.presentation.add_day.state.AddDayIntent
import antuere.how_are_you.presentation.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.add_day.state.AddDayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddDayViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase
) : ContainerHost<AddDayState, AddDaySideEffect>, ViewModel() {

    override val container: Container<AddDayState, AddDaySideEffect> = container(AddDayState())

    fun onIntent(intent: AddDayIntent) = intent {
        when (intent) {
            is AddDayIntent.SmileClicked -> viewModelScope.launch(Dispatchers.IO) {
                addDayUseCase(intent.day)
                postSideEffect(AddDaySideEffect.NavigateUp)
            }
            is AddDayIntent.DayDescChanged -> reduce {
                this.state.copy(dayDesc = intent.value)
            }
        }
    }
}