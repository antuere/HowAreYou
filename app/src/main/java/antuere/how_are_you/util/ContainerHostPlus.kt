package antuere.how_are_you.util

import org.orbitmvi.orbit.ContainerHost

interface ContainerHostPlus<state : Any, sideEffect : Any, intent : Any> :
    ContainerHost<state, sideEffect> {

    fun onIntent(intent: intent)
}