package antuere.how_are_you.presentation.cats

import androidx.lifecycle.ViewModel
import antuere.how_are_you.presentation.cats.state.CatsIntent
import antuere.how_are_you.presentation.cats.state.CatsState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor() :
    ContainerHostPlus<CatsState, Nothing, CatsIntent>, ViewModel() {

    override val container: Container<CatsState, Nothing> = container(CatsState())

    override fun onIntent(intent: CatsIntent) = intent {
        when (intent) {
            CatsIntent.UpdateCatsClicked -> reduce {
                state.copy(urlList = state.urlList.reversed())
            }
        }
    }
}