package com.example.zeroapp.presentation.summaryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.Day
import antuere.domain.usecases.GetCurrentDateUseCase
import antuere.domain.usecases.UpdateLastDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val updateLastDayUseCase: UpdateLastDayUseCase,
    private val getCurrentDateUseCase: GetCurrentDateUseCase
) :
    ViewModel() {

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private val _hideAddButton = MutableLiveData(false)
    val hideAddButton: LiveData<Boolean>
        get() = _hideAddButton

    init {
        Timber.i("fix! sum viewModel: init sumModel")
        updateInfo()
    }

    fun updateInfo() {
        viewModelScope.launch {

            _lastDay.value = updateLastDayUseCase.invoke()

            _hideAddButton.value =
                getCurrentDateUseCase.invoke() == (_lastDay.value?.currentDateString ?: "offWish")

            Timber.i("fix! sum viewModel: ${_hideAddButton.value}")
        }
    }


}