package antuere.how_are_you.presentation.mental_tips

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.mental_tips.TipsCategoryName
import antuere.domain.repository.MentalTipsRepository
import antuere.domain.util.Constants
import antuere.how_are_you.R
import antuere.how_are_you.presentation.mental_tips.state.MentalTipsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MentalTipsViewModel @Inject constructor(
    private val mentalTipsRepository: MentalTipsRepository,
    state: SavedStateHandle
) : ContainerHost<MentalTipsState, Nothing>, ViewModel() {

    override val container: Container<MentalTipsState, Nothing> =
        container(MentalTipsState.Loading())

    private val selectedCategory = state.get<String>(Constants.CATEGORY_KEY)!!
    private var labelId = 0

    init {
        val tipsCategoryName = mappingSelectedCategory(selectedCategory)

        viewModelScope.launch(Dispatchers.IO) {
            val listMentalTips = mentalTipsRepository.getMentalTips(tipsCategoryName)
            intent {
                reduce {
                    MentalTipsState.Loaded(
                        titleId = labelId,
                        listMentalTips = listMentalTips
                    )
                }
            }
        }
    }

    private fun mappingSelectedCategory(category: String): TipsCategoryName {
        return when (category) {
            TipsCategoryName.CURRENT_TIMES.name -> {
                labelId = R.string.current_time_tips
                TipsCategoryName.CURRENT_TIMES
            }
            TipsCategoryName.YOU_SELF.name -> {
                labelId = R.string.youself_tips
                TipsCategoryName.YOU_SELF
            }
            TipsCategoryName.FAMILY.name -> {
                labelId = R.string.family_tips
                TipsCategoryName.FAMILY
            }
            TipsCategoryName.RELATIONSHIP.name -> {
                labelId = R.string.relationship_tips
                TipsCategoryName.RELATIONSHIP
            }
            TipsCategoryName.FRIENDS.name -> {
                labelId = R.string.friends_tips
                TipsCategoryName.FRIENDS
            }
            else -> throw IllegalArgumentException("Wrong category name")
        }
    }
}