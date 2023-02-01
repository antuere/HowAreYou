package antuere.how_are_you.presentation.cats

import antuere.how_are_you.presentation.cats.state.CatsIntent
import antuere.how_are_you.presentation.cats.state.CatsState
import antuere.how_are_you.presentation.base.ViewModelMvi
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor() :
    ViewModelMvi<CatsState, Nothing, CatsIntent>() {

    override val container: Container<CatsState, Nothing> = container(CatsState())

    override fun onIntent(intent: CatsIntent) {
        when (intent) {
            CatsIntent.UpdateCatsClicked -> updateState {
                state.copy(urlList = state.urlList.reversed())
            }
        }
    }
}