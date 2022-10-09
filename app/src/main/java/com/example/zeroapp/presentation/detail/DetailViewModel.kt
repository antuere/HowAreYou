package com.example.zeroapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.usecases.DeleteDayUseCase
import antuere.domain.usecases.GetDayByIdUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    state: SavedStateHandle
) : ViewModel(),IUIDialogAction {

    private val _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private val dayId = state.get<Long>("dayId")
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
            _currentDay.value = getDayByIdUseCase.invoke(dayId!!)
        }
    }


    fun navigateDone() {
        _navigateToHistory.value = false
    }

    fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase.invoke(dayId!!)
        }
        _navigateToHistory.value = true
    }

    fun onDeleteButtonClicked(){
        _uiDialog.value = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_message,
            positiveButton = UIDialog.UiButton(R.string.yes){
                _uiDialog.value = null
            },
            negativeButton = UIDialog.UiButton(R.string.no){
                _uiDialog.value = null
            }
        )
    }
}