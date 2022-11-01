package com.example.zeroapp.presentation.history

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.dto.Day
import antuere.domain.usecases.*
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
    private val sharedPreferences: SharedPreferences,
    private val transitionName: String,
) :
    ViewModel(), IUIDialogAction, IUIDatePickerAction {

    companion object {
        private const val CHECKED_BUTTON_HISTORY_PREF = "checked button in history"

        const val CHECKED_ALL_DAYS = 1
        const val CHECKED_CURRENT_MONTH = 2
        const val CHECKED_LAST_WEEK = 3
    }

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

    private var _toggleButtonState = MutableLiveData<ToggleButtonState>()
    val toggleButtonState: LiveData<ToggleButtonState>
        get() = _toggleButtonState

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
            deleteDayUseCase.invoke(_dayId)
        }
    }

    fun onClickLongSmile() {
        _uiDialog.value = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_message,
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
                    _toggleButtonState.value = ToggleButtonState.Filter(kotlinPair)
                    _uiDatePicker.value = null
                }),
            negativeButton = UIDatePicker.UiButtonNegative(
                onClick = {
                    _uiDatePicker.value = null
                })
        )
    }

    fun checkedFilterButton(pair: Pair<Long, Long>) {
        viewModelScope.launch {
            getSelectedDaysUseCase.invoke(pair).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun checkedCurrentMonthButton() {
        viewModelScope.launch {
            getCertainDaysUseCase.invoke(TimeUtility.getCurrentMonthTime()).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun checkedLastWeekButton() {
        viewModelScope.launch {
                getCertainDaysUseCase.invoke(TimeUtility.getCurrentWeekTime()).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun checkedAllDaysButton() {
        viewModelScope.launch {
            getAllDaysUseCase.invoke(Unit).collectLatest {
                _listDays.postValue(it)
            }
        }
    }

    fun onClickCheckedItem(state: Int) {
        saveCheckedButtonState(state)
        getToggleButtonState()
    }

    private fun saveCheckedButtonState(state: Int) {
        sharedPreferences.edit().apply {
            putInt(CHECKED_BUTTON_HISTORY_PREF, state)
            apply()
        }
    }

    fun getToggleButtonState() {
        val sharedCheckedButtonState = sharedPreferences.getInt(CHECKED_BUTTON_HISTORY_PREF, -1)
        when (sharedCheckedButtonState) {
            CHECKED_ALL_DAYS -> _toggleButtonState.value = ToggleButtonState.AllDays
            CHECKED_CURRENT_MONTH -> _toggleButtonState.value = ToggleButtonState.CurrentMonth
            CHECKED_LAST_WEEK -> _toggleButtonState.value = ToggleButtonState.LastWeek
            -1 -> _toggleButtonState.value = ToggleButtonState.AllDays
        }
    }
}


