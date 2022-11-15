package com.example.zeroapp.presentation.history

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.dto.Day
import antuere.domain.dto.ToggleBtnState
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetAllDaysUseCase
import antuere.domain.usecases.days_entities.GetCertainDaysUseCase
import antuere.domain.usecases.days_entities.GetSelectedDaysUseCase
import antuere.domain.usecases.user_settings.GetToggleBtnStateUseCase
import antuere.domain.usecases.user_settings.SaveToggleBtnUseCase
import antuere.domain.util.TimeUtility
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_date_picker.IUIDatePickerAction
import com.example.zeroapp.presentation.base.ui_date_picker.UIDatePicker
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.presentation.history.adapter.DayClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllDaysUseCase: GetAllDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val getSelectedDaysUseCase: GetSelectedDaysUseCase,
    private val getCertainDaysUseCase: GetCertainDaysUseCase,
    private val getToggleBtnStateUseCase: GetToggleBtnStateUseCase,
    private val saveToggleBtnUseCase: SaveToggleBtnUseCase,
    private val transitionName: String,
) :
    ViewModel(), IUIDialogAction, IUIDatePickerAction {

    private var _dayId = 0L

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _uiDatePicker = MutableStateFlow<UIDatePicker?>(null)
    override val datePicker: StateFlow<UIDatePicker?>
        get() = _uiDatePicker

    private var _listDays = MutableLiveData<List<Day>>()
    val listDays: LiveData<List<Day>>
        get() = _listDays

    private var _navigateToDetailState = MutableLiveData<NavigateToDetailState>()
    val navigateToDetailState: LiveData<NavigateToDetailState>
        get() = _navigateToDetailState

    private var _toggleBtnState = MutableLiveData<ToggleBtnState>()
    val toggleBtnState: LiveData<ToggleBtnState>
        get() = _toggleBtnState

    private var _isFilterSelected = MutableLiveData<Boolean>()
    val isFilterSelected: LiveData<Boolean>
        get() = _isFilterSelected

    init {
        getToggleButtonState()
    }

    val dayClickListener = object : DayClickListener {
        override fun onClick(day: Day, view: View) {
            val extras = FragmentNavigatorExtras(view to transitionName)

            _navigateToDetailState.value = NavigateToDetailState(
                extras = extras,
                dayId = day.dayId,
                navigateToDetail = true
            )
        }

        override fun onClickLong(day: Day) {
            _dayId = day.dayId
            onClickLongSmile()
        }
    }

    fun doneNavigateToDetail() {
        _navigateToDetailState.value!!.navigateToDetail = false
    }


    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase(_dayId)
        }
    }

    fun onClickLongSmile() {
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
                })
        )
    }

    fun onClickFilterButton() {
        _uiDatePicker.value = UIDatePicker(
            title = R.string.date_picker_title,
            positiveButton = UIDatePicker.UiButtonPositive(
                onClick = {
                    val kotlinPair: Pair<Long, Long> = Pair(it.first, it.second)
                    checkedFilterButton(kotlinPair)
                    _isFilterSelected.value = true
                    _uiDatePicker.value = null
                }),
            negativeButton = UIDatePicker.UiButtonNegative(
                onClick = {
                    _uiDatePicker.value = null
                })
        )
    }

    private fun checkedFilterButton(pair: Pair<Long, Long>) {
        viewModelScope.launch {
            getSelectedDaysUseCase(pair).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun checkedCurrentMonthButton() {
        viewModelScope.launch {
            getCertainDaysUseCase(TimeUtility.getCurrentMonthTime()).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun checkedLastWeekButton() {
        viewModelScope.launch {
            getCertainDaysUseCase(TimeUtility.getCurrentWeekTime()).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun checkedAllDaysButton() {
        viewModelScope.launch {
            getAllDaysUseCase(Unit).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun onClickCheckedItem(state: ToggleBtnState) {
        viewModelScope.launch {
            saveToggleBtnUseCase(state)
        }
    }

    private fun getToggleButtonState() {
        viewModelScope.launch {
            getToggleBtnStateUseCase(Unit).collectLatest {
                _toggleBtnState.postValue(it)
            }
        }
    }

    fun resetIsFilterSelected() {
        _isFilterSelected.value = false
    }
}


