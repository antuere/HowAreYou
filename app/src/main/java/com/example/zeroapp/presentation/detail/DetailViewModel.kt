package com.example.zeroapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetDayByIdUseCase
import antuere.domain.usecases.days_entities.UpdateDayUseCase
import antuere.domain.util.Constants
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val updateDayUseCase: UpdateDayUseCase,
    state: SavedStateHandle,
) : ViewModel() {

    private val dayId = state.get<String>(Constants.DAY_ID_KEY)!!.toLong()

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private val _selectedDay = MutableStateFlow<Day?>(null)
    val selectedDay: StateFlow<Day?>
        get() = _selectedDay

    private val _navigateToHistory = MutableStateFlow(false)
    val navigateToHistory: StateFlow<Boolean>
        get() = _navigateToHistory

    init {
        getDay()
    }

    private fun getDay() {
        viewModelScope.launch {
            _selectedDay.value = getDayByIdUseCase(dayId)
        }
    }

    fun navigateDone() {
        _navigateToHistory.value = false
    }

    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase(dayId)
        }
        _navigateToHistory.value = true
    }

    fun onClickDeleteButton() {
        _uiDialog.value = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_desc,
            icon = R.drawable.ic_delete_black,
            positiveButton = UIDialog.UiButton(
                text = R.string.yes,
                onClick = {
                    deleteDay()
                    _uiDialog.value = null
                }),
            negativeButton = UIDialog.UiButton(
                text = R.string.no,
                onClick = {
                    _uiDialog.value = null
                }),
            dismissAction = {
                _uiDialog.value = null
            }
        )
    }

    fun onClickFavoriteButton() {
        viewModelScope.launch {
            _selectedDay.value!!.isFavorite = _selectedDay.value!!.isFavorite.not()
            updateDayUseCase(_selectedDay.value!!)
        }
    }
}