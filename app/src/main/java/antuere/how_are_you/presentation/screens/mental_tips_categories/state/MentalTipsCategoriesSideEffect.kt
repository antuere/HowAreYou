package antuere.how_are_you.presentation.screens.mental_tips_categories.state

sealed interface MentalTipsCategoriesSideEffect {
    data class NavigateToMentalTips(val categoryName: String) : MentalTipsCategoriesSideEffect
}