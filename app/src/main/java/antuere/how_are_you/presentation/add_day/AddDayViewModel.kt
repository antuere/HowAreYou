package antuere.how_are_you.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.usecases.AddDayUseCase
import antuere.how_are_you.presentation.add_day.state.AddDayIntent
import antuere.how_are_you.presentation.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.add_day.state.AddDayState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class AddDayViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase,
) : ContainerHostPlus<AddDayState, AddDaySideEffect, AddDayIntent>, ViewModel() {

    override val container: Container<AddDayState, AddDaySideEffect> = container(AddDayState())

    override fun onIntent(intent: AddDayIntent) {
        when (intent) {
            is AddDayIntent.DayDescChanged -> blockingIntent {
                reduce {
                    state.copy(dayDesc = intent.value)
                }
            }
            is AddDayIntent.SmileClicked -> intent {
                viewModelScope.launch(Dispatchers.IO) {
                    addDayUseCase(intent.day)
                    postSideEffect(AddDaySideEffect.NavigateUp)
                }
            }
        }
    }
}