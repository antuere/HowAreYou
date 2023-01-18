package com.example.zeroapp.presentation.mental_tips

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.TipsCategoryName
import antuere.domain.repository.MentalTipsRepository
import antuere.domain.util.Constants
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MentalTipsViewModel @Inject constructor(
    private val mentalTipsRepository: MentalTipsRepository,
    state: SavedStateHandle,
) : ViewModel() {

    private val selectedCategory = state.get<String>(Constants.CATEGORY_KEY)!!

    private val _listMentalTips = MutableStateFlow<List<MentalTip>>(emptyList())
    val listMentalTips: StateFlow<List<MentalTip>>
        get() = _listMentalTips

    private val _screenLabelId = MutableStateFlow(R.string.mental_tips)
    val screenLabelId: StateFlow<Int>
        get() = _screenLabelId

    init {
        val tipsCategoryName = mappingSelectedCategory(selectedCategory)

        viewModelScope.launch(Dispatchers.IO) {
            _listMentalTips.value = mentalTipsRepository.getMentalTips(tipsCategoryName)
        }
    }


    private fun mappingSelectedCategory(category: String): TipsCategoryName {
        return when (category) {
            TipsCategoryName.CURRENT_TIMES.name -> {
                _screenLabelId.value = R.string.current_time_tips
                TipsCategoryName.CURRENT_TIMES
            }
            TipsCategoryName.YOU_SELF.name -> {
                _screenLabelId.value = R.string.youself_tips
                TipsCategoryName.YOU_SELF
            }
            TipsCategoryName.FAMILY.name -> {
                _screenLabelId.value = R.string.family_tips
                TipsCategoryName.FAMILY
            }
            TipsCategoryName.RELATIONSHIP.name -> {
                _screenLabelId.value = R.string.relationship_tips
                TipsCategoryName.RELATIONSHIP
            }
            TipsCategoryName.FRIENDS.name -> {
                _screenLabelId.value = R.string.friends_tips
                TipsCategoryName.FRIENDS
            }
            else -> throw IllegalArgumentException("Wrong category name")
        }
    }
}