package antuere.how_are_you.presentation.mental_tips_categories.state

sealed interface MentalTipsCategoriesIntent {
    data class TipsCategorySelected(val selectedCategory: String) : MentalTipsCategoriesIntent
}