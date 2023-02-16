package antuere.how_are_you.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.util.Constants
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent
import antuere.how_are_you.presentation.screens.detail.state.DetailSideEffect
import antuere.how_are_you.presentation.screens.detail.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    state: SavedStateHandle,
) : ViewModelMvi<DetailState, DetailSideEffect, DetailIntent>() {

    override val container: Container<DetailState, DetailSideEffect> =
        container(DetailState())

    private val dayId = state.get<Long>(Constants.DAY_ID_KEY)!!

    init {
        getDay()
    }

    override fun onIntent(intent: DetailIntent) {
        when (intent) {
            DetailIntent.DeleteBtnClicked -> {
                val dialog = UIDialog(
                    title = R.string.dialog_delete_title,
                    desc = R.string.dialog_delete_desc,
                    icon = R.drawable.ic_delete_black,
                    positiveButton = UIDialog.UiButton(
                        text = R.string.yes,
                        onClick = {
                            deleteDay()
                        }),
                    negativeButton = UIDialog.UiButton(
                        text = R.string.no,
                        onClick = {})
                )
                sideEffect(DetailSideEffect.Dialog(dialog))
            }
            DetailIntent.FavoriteBtnClicked -> {
                sideEffect(DetailSideEffect.AnimateFavoriteBtn)
                var isFavorite = true
                val newFabBtnRes =
                    if (state.favoriteBtnRes == R.drawable.ic_baseline_favorite_border) {
                        R.drawable.ic_baseline_favorite
                    } else {
                        isFavorite = false
                        R.drawable.ic_baseline_favorite_border
                    }
                updateState {
                    state.copy(
                        favoriteBtnRes = newFabBtnRes
                    )
                }
                viewModelScope.launch(Dispatchers.IO) {
                    val newDay = Day(
                        dayId = dayId,
                        imageResId = state.daySmileRes,
                        dayText = state.dayText,
                        dateString = state.dateString,
                        isFavorite = isFavorite
                    )
                    dayRepository.update(newDay)
                }
            }
        }
    }

    private fun getDay() {
        viewModelScope.launch(Dispatchers.IO) {
            val day = dayRepository.getDayById(dayId).first()!!

            val favBtnRes = if (day.isFavorite) {
                R.drawable.ic_baseline_favorite
            } else {
                R.drawable.ic_baseline_favorite_border
            }

            updateState {
                state.copy(
                    isLoading = false,
                    daySmileRes = day.imageResId,
                    dayText = day.dayText,
                    dateString = day.dateString,
                    favoriteBtnRes = favBtnRes
                )
            }
        }
    }

    private fun deleteDay() {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.deleteDay(dayId)
        }
        sideEffect(DetailSideEffect.NavigateUp)
    }
}