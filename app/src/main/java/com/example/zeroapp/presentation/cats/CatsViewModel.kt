package com.example.zeroapp.presentation.cats

import androidx.lifecycle.ViewModel
import com.example.zeroapp.presentation.cats.state.CatsState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor() :
    ContainerHost<CatsState, Nothing>, ViewModel() {

    override val container: Container<CatsState, Nothing> = container(CatsState())

    fun onClickUpdateCats() = intent {
        reduce {
            state.copy(urlList = state.urlList.reversed())
        }
    }
}