package com.example.zeroapp.presentation.cats

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CatsViewModel @Inject constructor() : ViewModel() {

    private var _urlList = MutableStateFlow(
        listOf(
            "https://source.unsplash.com/random/?cutecats",
            "https://source.unsplash.com/random/?feline",
            "https://source.unsplash.com/random/?cat",
            "https://source.unsplash.com/random/?kitty"
        )
    )
    val urlList: StateFlow<List<String>>
        get() = _urlList


    fun onClickUpdateCats() {
        _urlList.value = _urlList.value.asReversed()
    }
}