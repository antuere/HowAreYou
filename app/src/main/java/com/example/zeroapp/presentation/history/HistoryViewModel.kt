package com.example.zeroapp.presentation.history

import androidx.lifecycle.*
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
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose
import com.example.zeroapp.presentation.base.ui_date_picker.IUIDatePickerAction
import com.example.zeroapp.presentation.base.ui_date_picker.UIDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    ViewModel(), IUIDatePickerAction {

    private var _dayId = 0L

    private var _uiDialog = MutableStateFlow<UIDialogCompose?>(null)
    val uiDialog: StateFlow<UIDialogCompose?>
        get() = _uiDialog

    private var _uiDatePicker = MutableStateFlow<UIDatePicker?>(null)
    override val datePicker: StateFlow<UIDatePicker?>
        get() = _uiDatePicker

    private var _listDays = MutableStateFlow<List<Day>>(emptyList())
    val listDays: StateFlow<List<Day>>
        get() = _listDays

    private var _navigateToDetailState = MutableStateFlow<NavigateToDetailState?>(null)
    val navigateToDetailState: StateFlow<NavigateToDetailState?>
        get() = _navigateToDetailState

    private var _toggleBtnState = MutableStateFlow(ToggleBtnState.CURRENT_MONTH)
    val toggleBtnState: StateFlow<ToggleBtnState>
        get() = _toggleBtnState

    private var _isFilterSelected = MutableStateFlow(false)
    val isFilterSelected: StateFlow<Boolean>
        get() = _isFilterSelected

    private var _currentJob: JobType? = null

    init {
        getToggleButtonState()
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


    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase(_dayId)
        }
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
        if (_currentJob !is JobType.Filter) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.Filter(viewModelScope.launch {
                getSelectedDaysUseCase(pair).cancellable().collectLatest {
                    _listDays.value = it
                }
            })
        }
    }

    fun checkedCurrentMonthButton() {
        if (_currentJob !is JobType.Month) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.Month(viewModelScope.launch {
                getCertainDaysUseCase(TimeUtility.getCurrentMonthTime()).cancellable()
                    .collectLatest {
                        _listDays.value = it
                    }
            })
        }
    }

    fun checkedLastWeekButton() {
        if (_currentJob !is JobType.Week) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.Week(viewModelScope.launch {
                getCertainDaysUseCase(TimeUtility.getCurrentWeekTime()).cancellable()
                    .collectLatest {
                        _listDays.value = it
                    }
            })
        }
    }

    fun checkedAllDaysButton() {
        if (_currentJob !is JobType.AllDays) {
            _currentJob?.job?.cancel()

            _currentJob = JobType.AllDays(viewModelScope.launch {
                getAllDaysUseCase(Unit).cancellable().collectLatest {
                    _listDays.value = it
                }
            })
        }
    }

    fun onClickCheckedItem(state: ToggleBtnState) {
        if (_toggleBtnState.value != state) {
            viewModelScope.launch {
                saveToggleBtnUseCase(state)
            }
        }
    }

    private fun getToggleButtonState() {
        viewModelScope.launch(Dispatchers.IO) {
            getToggleBtnStateUseCase(Unit).collectLatest {
                _toggleBtnState.value = it
            }
        }
    }

    fun resetIsFilterSelected() {
        _isFilterSelected.value = false
    }
}


