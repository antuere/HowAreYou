package com.example.zeroapp.presentation.helplines

import androidx.lifecycle.ViewModel
import com.example.zeroapp.presentation.base.ui_text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HelplinesViewModel @Inject constructor(

) : ViewModel() {

    private var _countryList = MutableStateFlow<List<UiText>?>(null)
    val countryList: StateFlow<List<UiText>?>
        get() = _countryList


}