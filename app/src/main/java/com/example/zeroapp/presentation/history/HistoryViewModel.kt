package com.example.zeroapp.presentation.history

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
import com.example.zeroapp.util.toMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    getAllDaysUseCase: GetAllDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val getSelectedDaysUseCase: GetSelectedDaysUseCase,
    private val getRequiresDaysUseCase: GetRequiresDaysUseCase,
    private val transitionName: String,
) :
    ViewModel(), IUIDialogAction, IUIDatePickerAction {

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _uiDatePicker = MutableStateFlow<UIDatePicker?>(null)
    override val datePicker: StateFlow<UIDatePicker?>
        get() = _uiDatePicker

    private var _listDays = MutableLiveData<List<Day>>()
    val listDays: LiveData<List<Day>>
        get() = _listDays

    private var _dayId = 0L

    private var _navigateToDetailState = MutableLiveData<NavigateToDetailState>()
    val navigateToDetailState: LiveData<NavigateToDetailState>
        get() = _navigateToDetailState

    init {
        viewModelScope.launch {
            _listDays =
                getAllDaysUseCase.invoke(Unit).asLiveData(Dispatchers.IO).toMutableLiveData()
        }
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
                    viewModelScope.launch {
                        _listDays.value =
                            getSelectedDaysUseCase.invoke(kotlinPair).firstOrNull() ?: emptyList()
                    }
                    _uiDatePicker.value = null
                }),
            negativeButton = UIDatePicker.UiButtonNegative(
                onClick = {
                    _uiDatePicker.value = null
                })

        )
    }

    fun onClickLastMonthButton() {
        viewModelScope.launch {
            _listDays.value =
                getRequiresDaysUseCase.invoke(TimeUtility.getCurrentMonthTime()).firstOrNull()
                    ?: emptyList()
        }
    }

}


