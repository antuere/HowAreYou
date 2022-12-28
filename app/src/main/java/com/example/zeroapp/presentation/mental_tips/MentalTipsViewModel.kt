package com.example.zeroapp.presentation.mental_tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.usecases.mental_tips.GetMentalTipsCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MentalTipsViewModel @Inject constructor(
    getMentalTipsCategoriesUseCase: GetMentalTipsCategoriesUseCase
) : ViewModel() {

    private var _listMentalTipsCategories = MutableStateFlow<List<MentalTipsCategory>>(emptyList())
    val listMentalTipsCategories: StateFlow<List<MentalTipsCategory>>
        get() = _listMentalTipsCategories

    init {
        viewModelScope.launch {
            _listMentalTipsCategories.value = getMentalTipsCategoriesUseCase(Unit)
        }
    }
}