package com.example.zeroapp.presentation.historyFragment

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.Day
import antuere.domain.usecases.DeleteDayUseCase
import antuere.domain.usecases.GetAllDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    getAllDaysUseCase: GetAllDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val transitionName: String
) :
    ViewModel(), DayClickListener {

    var listDays: LiveData<List<Day>> = getAllDaysUseCase.invoke().asLiveData(Dispatchers.Main)


    private var _extras = MutableLiveData<FragmentNavigator.Extras>()
    val extras: LiveData<FragmentNavigator.Extras>
        get() = _extras

    private var _dayId = MutableLiveData<Long>()
    val dayId: LiveData<Long>
        get() = _dayId

    private var _navigateToDetail = MutableLiveData(false)
    val navigateToDetail: LiveData<Boolean>
        get() = _navigateToDetail

    private var _showDialogDelete = MutableLiveData(false)
    val showDialogDelete: LiveData<Boolean>
        get() = _showDialogDelete


    override fun onClick(day: Day, view: View) {
        val extras = FragmentNavigatorExtras(view to transitionName)
        _extras.value = extras

        _dayId.value = day.dayId
        _navigateToDetail.value = true
    }

    override fun onClickLong(day: Day) {
        _dayId.value = day.dayId
        _showDialogDelete.value = true
    }

    fun doneNavigateToDetail() {
        _navigateToDetail.value = false
    }

    fun doneShowDialogDelete() {
        _showDialogDelete.value = false
    }

    fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase.invoke(_dayId.value!!)
        }
    }
}


