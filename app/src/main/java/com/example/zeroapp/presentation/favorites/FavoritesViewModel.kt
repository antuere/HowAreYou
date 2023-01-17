package com.example.zeroapp.presentation.favorites

import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetFavoritesDaysUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.favorites.state.FavoritesSideEffect
import com.example.zeroapp.presentation.favorites.state.FavoritesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesDaysUseCase: GetFavoritesDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
) : ContainerHost<FavoritesState, FavoritesSideEffect>, ViewModel() {

    override val container: Container<FavoritesState, FavoritesSideEffect> =
        container(FavoritesState.LoadingShimmer())

    init {
        getFavoritesDays()
    }

    fun onClickDay(day: Day) = intent {
        postSideEffect(
            FavoritesSideEffect.NavigationToDayDetail(
                dayId = day.dayId
            )
        )
    }

    fun onClickLongDay(day: Day) = intent {
        val uiDialog = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_desc,
            icon = R.drawable.ic_delete_black,
            positiveButton = UIDialog.UiButton(
                text = R.string.yes,
                onClick = {
                    deleteDay(day.dayId)
                }),
            negativeButton = UIDialog.UiButton(text = R.string.no)
        )
        postSideEffect(FavoritesSideEffect.Dialog(uiDialog))
    }

    private fun getFavoritesDays() = intent {
        viewModelScope.launch {
            getFavoritesDaysUseCase(Unit).collectLatest { favDays ->
                reduce {
                    FavoritesState.Loaded(dayList = favDays)
                }
            }
        }
    }

    private fun deleteDay(dayId: Long) {
        viewModelScope.launch {
            deleteDayUseCase(dayId)
        }
    }
}