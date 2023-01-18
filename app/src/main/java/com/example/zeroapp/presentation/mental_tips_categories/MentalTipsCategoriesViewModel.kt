package com.example.zeroapp.presentation.mental_tips_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.repository.MentalTipsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MentalTipsCategoriesViewModel @Inject constructor(
    mentalTipsRepository: MentalTipsRepository
) : ViewModel() {

    private var _listMentalTipsCategories = MutableStateFlow<List<MentalTipsCategory>>(emptyList())
    val listMentalTipsCategories: StateFlow<List<MentalTipsCategory>>
        get() = _listMentalTipsCategories

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _listMentalTipsCategories.value = mentalTipsRepository.getMentalTipsCategories()
        }
    }
}