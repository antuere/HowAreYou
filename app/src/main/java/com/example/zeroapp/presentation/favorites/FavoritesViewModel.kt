package com.example.zeroapp.presentation.favorites

import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetFavoritesDaysUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose
import com.example.zeroapp.presentation.history.NavigateToDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritesDaysUseCase: GetFavoritesDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val transitionName: String
) : ViewModel() {

    private var _uiDialog = MutableStateFlow<UIDialogCompose?>(null)
    val uiDialog: StateFlow<UIDialogCompose?>
        get() = _uiDialog

    private var _listDays = MutableStateFlow<List<Day>>(emptyList())
    val listDays: StateFlow<List<Day>>
        get() = _listDays

    private var _navigateToDetailState = MutableStateFlow<NavigateToDetailState?>(null)
    val navigateToDetailState: StateFlow<NavigateToDetailState?>
        get() = _navigateToDetailState

    private var _dayId = 0L

    init {
        viewModelScope.launch {
            getFavoritesDaysUseCase(Unit).collectLatest {
                _listDays.value = it
            }
        }
    }

    fun onClickDay(day: Day) {
        _navigateToDetailState.value = NavigateToDetailState(
            dayId = day.dayId,
            navigateToDetail = true
        )
    }

    fun onClickLongDay(day: Day) {
        _dayId = day.dayId
        onClickLongSmile()
    }


    fun doneNavigateToDetail() {
        _navigateToDetailState.value!!.navigateToDetail = false
    }

    private fun onClickLongSmile() {
        _uiDialog.value = UIDialogCompose(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_desc,
            icon = R.drawable.ic_delete_black,
            positiveButton = UIDialogCompose.UiButton(
                text = R.string.yes,
                onClick = {
                    deleteDay()
                    _uiDialog.value = null
                }),
            negativeButton = UIDialogCompose.UiButton(
                text = R.string.no,
                onClick = {
                    _uiDialog.value = null
                }),
            dismissAction = {
                _uiDialog.value = null
            }
        )
    }

    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase(_dayId)
        }
    }
}