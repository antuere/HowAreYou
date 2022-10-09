package com.example.zeroapp.presentation.history

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.dto.Day
import antuere.domain.usecases.DeleteDayUseCase
import antuere.domain.usecases.GetAllDaysUseCase
import com.example.zeroapp.presentation.history.adapter.DayClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

object Example {
    data class UiState(
        val extras: FragmentNavigator.Extras? = null,
        val dayID: Long? = null,
        val showDialogDelete: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState
}


@HiltViewModel
class HistoryViewModel @Inject constructor(
    getAllDaysUseCase: GetAllDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val transitionName: String
) :
    ViewModel() {

    // TODO Неправильно сделано
    // Не должно быть инициализаии юз-кейсом
    var listDays: LiveData<List<Day>> =
        runBlocking { getAllDaysUseCase.invoke(Unit) }.asLiveData(Dispatchers.Main)

    /** TODO Иногда несколько полей можно объединить в одно, чтобы создать UI State:
     * Подробнее: [Example]
     **/
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

    val dayClickListener = object : DayClickListener {
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


