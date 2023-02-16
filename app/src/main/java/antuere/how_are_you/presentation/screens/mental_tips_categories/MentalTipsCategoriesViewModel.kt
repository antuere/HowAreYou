package antuere.how_are_you.presentation.screens.mental_tips_categories

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.MentalTipsRepository
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.mental_tips_categories.state.MentalTipsCategoriesIntent
import antuere.how_are_you.presentation.screens.mental_tips_categories.state.MentalTipsCategoriesSideEffect
import antuere.how_are_you.presentation.screens.mental_tips_categories.state.MentalTipsCategoriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MentalTipsCategoriesViewModel @Inject constructor(
    mentalTipsRepository: MentalTipsRepository,
) : ViewModelMvi<MentalTipsCategoriesState, MentalTipsCategoriesSideEffect, MentalTipsCategoriesIntent>() {

    override val container: Container<MentalTipsCategoriesState, MentalTipsCategoriesSideEffect> =
        container(MentalTipsCategoriesState.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listMentalTipsCategories = mentalTipsRepository.getMentalTipsCategories()
            updateState {
                MentalTipsCategoriesState.Loaded(listMentalTipsCategories)
            }
        }
    }

    override fun onIntent(intent: MentalTipsCategoriesIntent) {
        when (intent) {
            is MentalTipsCategoriesIntent.TipsCategorySelected -> {
                sideEffect(MentalTipsCategoriesSideEffect.NavigateToMentalTips(intent.selectedCategory))
            }
        }
    }

}