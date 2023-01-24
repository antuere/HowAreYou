package com.example.zeroapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.util.Constants
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.detail.state.DetailSideEffect
import com.example.zeroapp.presentation.detail.state.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    state: SavedStateHandle,
) : ContainerHost<DetailState, DetailSideEffect>, ViewModel() {

    override val container: Container<DetailState, DetailSideEffect> =
        container(DetailState())

    private val dayId = state.get<String>(Constants.DAY_ID_KEY)!!.toLong()

    init {
        getDay()
    }

    fun onClickDeleteButton() = intent {
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
        postSideEffect(DetailSideEffect.Dialog(dialog))
    }

    fun onClickFavoriteButton() = intent {
        postSideEffect(DetailSideEffect.AnimateFavoriteBtn)

        val newFabBtnRes =
            if (state.isFavorite) {
                R.drawable.ic_baseline_favorite_border
            } else {
                R.drawable.ic_baseline_favorite
            }

        reduce {
            state.copy(
                isFavorite = state.isFavorite.not(),
                favoriteBtnRes = newFabBtnRes
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val newDay = Day(
                dayId = dayId,
                imageResId = state.daySmileRes,
                dayText = state.dayText,
                dateString = state.dateString,
                isFavorite = state.isFavorite
            )
            dayRepository.update(newDay)
        }
    }

    private fun getDay() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            val day = dayRepository.getDayById(dayId)!!

            val favBtnRes = if (day.isFavorite) {
                R.drawable.ic_baseline_favorite
            } else {
                R.drawable.ic_baseline_favorite_border
            }

            reduce {
                state.copy(
                    isLoading = false,
                    daySmileRes = day.imageResId,
                    dayText = day.dayText,
                    dateString = day.dateString,
                    isFavorite = day.isFavorite,
                    favoriteBtnRes = favBtnRes
                )
            }
        }
    }

    private fun deleteDay() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.deleteDay(dayId)
        }
        postSideEffect(DetailSideEffect.NavigateUp)
    }
}