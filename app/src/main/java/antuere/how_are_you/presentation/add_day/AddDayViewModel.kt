package antuere.how_are_you.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.AddDayUseCase
import antuere.how_are_you.presentation.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.add_day.state.AddDayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddDayViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase
    ) : ContainerHost<AddDayState, AddDaySideEffect>,  ViewModel() {

    override val container: Container<AddDayState, AddDaySideEffect>
        = container(AddDayState())


    fun onClickSmile(imageResId: Int, descDay: String) = intent {
        val day = Day(imageResId = imageResId, dayText = descDay)

        viewModelScope.launch(Dispatchers.IO) {
            addDayUseCase(day)
        }
        postSideEffect(AddDaySideEffect.NavigateUp)
    }
}