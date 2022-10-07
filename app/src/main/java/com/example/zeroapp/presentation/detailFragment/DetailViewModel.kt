package com.example.zeroapp.presentation.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.Day
import antuere.domain.usecases.DeleteDayUseCase
import antuere.domain.usecases.GetDayByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    state: SavedStateHandle
) : ViewModel() {


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
}