package com.example.zeroapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetDayByIdUseCase
import antuere.domain.usecases.days_entities.UpdateDayUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
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
) : ViewModel(), IUIDialogAction {

    private val dayId = state.get<Long>("dayId")

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private val _currentDay = MutableLiveData<Day?>()
    val currentDay: LiveData<Day?>
        get() = _currentDay

    private val _navigateToHistory = MutableLiveData(false)
    val navigateToHistory: LiveData<Boolean>
        get() = _navigateToHistory

    init {
        getDay()
    }

    private fun getDay() {
        viewModelScope.launch {
            _currentDay.value = getDayByIdUseCase(dayId!!)
        }
    }

    fun navigateDone() {
        _navigateToHistory.value = false
    }

    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase(dayId!!)
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
                    navigateDone()
                    _uiDialog.value = null
                }),
            negativeButton = UIDialog.UiButton(
                text = R.string.no,
                onClick = {
                    _uiDialog.value = null
                })
        )
    }

    fun onClickFavoriteButton() {
        viewModelScope.launch {
            _currentDay.value!!.isFavorite = _currentDay.value!!.isFavorite.not()
            updateDayUseCase(_currentDay.value!!)
        }
    }
}