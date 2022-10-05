package com.example.zeroapp.presentation.summaryFragment

import android.text.format.DateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.Day
import antuere.domain.repository.DayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(private val dayRepository: DayRepository) : ViewModel() {

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private val _hideAddButton = MutableLiveData(false)
    val hideAddButton: LiveData<Boolean>
        get() = _hideAddButton

    init {
        Timber.i("fix! sum viewModel: init sumModel")
        updateLastDay()
    }

    fun updateLastDay() {
        viewModelScope.launch {
            Timber.i("fix! sum viewModel: enter in upd")

            val currentDate = DateFormat.format("dd/MM/yy", System.currentTimeMillis()).toString()
            _lastDay.value = dayRepository.getDay()

            Timber.i("fix! sum viewModel: change in upd")
            Timber.i("fix! sum viewModel: lastDay is ${_lastDay.value}")

            _hideAddButton.value = currentDate == (_lastDay.value?.currentDateString ?: "offWish")

            Timber.i("fix! sum viewModel: ${_hideAddButton.value}")
        }
    }


}