package com.example.zeroapp.presentation.history

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.dto.Day
import antuere.domain.usecases.DeleteDayUseCase
import antuere.domain.usecases.GetAllDaysUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.presentation.history.adapter.DayClickListener
import com.example.zeroapp.util.toMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    getAllDaysUseCase: GetAllDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val transitionName: String,
) :
    ViewModel(), IUIDialogAction {

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _listDays = MutableLiveData<List<Day>>()
    val listDays: LiveData<List<Day>>
        get() = _listDays

    private var _extras = MutableLiveData<FragmentNavigator.Extras>()
    val extras: LiveData<FragmentNavigator.Extras>
        get() = _extras

    private var _dayId = MutableLiveData<Long>()
    val dayId: LiveData<Long>
        get() = _dayId

    private var _navigateToDetail = MutableLiveData(false)
    val navigateToDetail: LiveData<Boolean>
        get() = _navigateToDetail


    init {
        runBlocking {
            _listDays =
                getAllDaysUseCase.invoke(Unit).asLiveData(Dispatchers.Main).toMutableLiveData()
        }
    }

    val dayClickListener = object : DayClickListener {
        override fun onClick(day: Day, view: View) {
            val extras = FragmentNavigatorExtras(view to transitionName)
            _extras.value = extras

            _dayId.value = day.dayId
            _navigateToDetail.value = true
        }

        override fun onClickLong(day: Day) {
            _dayId.value = day.dayId
            onSmileClickedLong()
        }
    }

    fun doneNavigateToDetail() {
        _navigateToDetail.value = false
    }


    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase.invoke(_dayId.value!!)
        }
    }

    fun onSmileClickedLong() {
        _uiDialog.value = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_message,
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
}


