package antuere.how_are_you.presentation.mental_tips_categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.MentalTipsRepository
import antuere.how_are_you.presentation.mental_tips_categories.state.MentalTipsCategoriesIntent
import antuere.how_are_you.presentation.mental_tips_categories.state.MentalTipsCategoriesSideEffect
import antuere.how_are_you.presentation.mental_tips_categories.state.MentalTipsCategoriesState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MentalTipsCategoriesViewModel @Inject constructor(
    mentalTipsRepository: MentalTipsRepository,
) : ContainerHostPlus<MentalTipsCategoriesState, MentalTipsCategoriesSideEffect, MentalTipsCategoriesIntent>,
    ViewModel() {

    override val container: Container<MentalTipsCategoriesState, MentalTipsCategoriesSideEffect> =
        container(MentalTipsCategoriesState.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val listMentalTipsCategories = mentalTipsRepository.getMentalTipsCategories()
            intent {
                reduce {
                    MentalTipsCategoriesState.Loaded(listMentalTipsCategories)
                }
            }
        }
    }

    override fun onIntent(intent: MentalTipsCategoriesIntent) = intent {
        when (intent) {
            is MentalTipsCategoriesIntent.TipsCategorySelected -> {
                postSideEffect(MentalTipsCategoriesSideEffect.NavigateToMentalTips(intent.selectedCategory))
            }
        }
    }

}