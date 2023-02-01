package antuere.how_are_you.presentation.mental_tips_categories.state

import antuere.domain.dto.mental_tips.MentalTipsCategory

sealed interface MentalTipsCategoriesState {
    object Loading : MentalTipsCategoriesState
    data class Loaded(val listCategories: List<MentalTipsCategory>) : MentalTipsCategoriesState
}