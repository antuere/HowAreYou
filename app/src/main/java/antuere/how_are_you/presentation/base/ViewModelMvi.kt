package antuere.how_are_you.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitDsl
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.annotation.OrbitInternal
import org.orbitmvi.orbit.idling.withIdling
import org.orbitmvi.orbit.syntax.simple.SimpleContext

abstract class ViewModelMvi<STATE : Any, SIDE_EFFECT : Any, INTENT : Any> :
    ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {

    val state: STATE
        get() = this.container.stateFlow.value

    abstract fun onIntent(intent: INTENT)

    @OptIn(OrbitInternal::class)
    @OrbitDsl
    fun updateState(
        reducer: SimpleContext<STATE>.() -> STATE,
    ) {
        viewModelScope.launch {
            container.orbit {
                withIdling(true) {
                    this.apply {
                        reduce { reducerState ->
                            SimpleContext(reducerState).reducer()
                        }
                    }
                }
            }
        }
    }

    @OptIn(OrbitInternal::class, OrbitExperimental::class)
    @OrbitDsl
    fun updateStateBlocking(
        reducer: SimpleContext<STATE>.() -> STATE,
    ) {
        viewModelScope.launch {
            container.inlineOrbit {
                withIdling(true) {
                    this.apply {
                        reduce { reducerState ->
                            SimpleContext(reducerState).reducer()
                        }
                    }
                }
            }
        }
    }

    @OptIn(OrbitInternal::class)
    @OrbitDsl
    fun sideEffect(
        sideEffect: SIDE_EFFECT,
    ) {
        viewModelScope.launch {
            container.orbit {
                withIdling(true) {
                    this.postSideEffect(sideEffect)
                }
            }
        }
    }
}