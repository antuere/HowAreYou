package antuere.how_are_you.presentation.screens.add_day


import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.AddDayUseCase
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.add_day.state.AddDayIntent
import antuere.how_are_you.presentation.screens.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.screens.add_day.state.AddDayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddDayViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase,
) : ViewModelMvi<AddDayState, AddDaySideEffect, AddDayIntent>() {

    override val container: Container<AddDayState, AddDaySideEffect> = container(AddDayState())

    override fun onIntent(intent: AddDayIntent) {
        when (intent) {
            is AddDayIntent.DayDescChanged -> updateStateBlocking {
                state.copy(dayDesc = intent.value)
            }
            is AddDayIntent.SmileClicked -> {
                val day = Day(imageResId = intent.imageResId, dayText = state.dayDesc)
                viewModelScope.launch(Dispatchers.IO) {
                    addDayUseCase(day)
                }
                sideEffect(AddDaySideEffect.NavigateUp)
            }
        }
    }
}