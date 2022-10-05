package com.example.zeroapp.presentation.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.util.MyExtendedViewModel
import antuere.domain.Day
import antuere.domain.repository.DayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    override var dayRepository: DayRepository,
    private val state: SavedStateHandle
) : MyExtendedViewModel() {

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
            val dayId = state.get<Long>("dayId")
            _currentDay.value = dayRepository.getDayById(dayId!!)
        }
    }


    fun navigateDone() {
        _navigateToHistory.value = false
    }

    override fun deleteDay(id: Long) {
        super.deleteDay(id)
        _navigateToHistory.value = true
    }
}