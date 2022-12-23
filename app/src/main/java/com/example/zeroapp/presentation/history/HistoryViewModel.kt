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
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
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
    ViewModel() {

    private var _dayId = 0L

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _listDays = MutableStateFlow<List<Day>>(emptyList())
    val listDays: StateFlow<List<Day>>
        get() = _listDays

    private var _navigateToDetailState = MutableStateFlow<NavigateToDetailState?>(null)
    val navigateToDetailState: StateFlow<NavigateToDetailState?>
        get() = _navigateToDetailState

    private var _toggleBtnState = MutableStateFlow(ToggleBtnState.ALL_DAYS)
    val toggleBtnState: StateFlow<ToggleBtnState>
        get() = _toggleBtnState

    private var _isDaysSelected = MutableStateFlow(false)
    val isDaysSelected: StateFlow<Boolean>
        get() = _isDaysSelected

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

    fun onDaysSelected(pair: Pair<Long, Long>) {
        _toggleBtnState.value = ToggleBtnState.FILTER_SELECTED
        saveToggleButtonState(_toggleBtnState.value)
        _isDaysSelected.value = true

        _currentJob?.job?.cancel()
        _currentJob = JobType.Filter(viewModelScope.launch {
            getSelectedDaysUseCase(pair).cancellable().collectLatest {
                _listDays.value = it
            }
        })
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
            saveToggleButtonState(state)
        }
    }

    private fun saveToggleButtonState(state: ToggleBtnState) {
        viewModelScope.launch {
            saveToggleBtnUseCase(state)
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
        _isDaysSelected.value = false
    }
}


