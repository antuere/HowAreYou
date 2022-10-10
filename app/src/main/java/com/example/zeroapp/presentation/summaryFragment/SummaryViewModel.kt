package com.example.zeroapp.presentation.summaryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.usecases.UpdateLastDayUseCase
import antuere.domain.util.TimeUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val updateLastDayUseCase: UpdateLastDayUseCase
) :
    ViewModel() {

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private val _hideAddButton = MutableLiveData<HideAddButtonState>(HideAddButtonState.Add)
    val hideAddButton: LiveData<HideAddButtonState>
        get() = _hideAddButton

    init {
        Timber.i("fix! sum viewModel: init sumModel")
        updateInfo()
    }

    fun updateInfo() {
        viewModelScope.launch {

            _lastDay.value = updateLastDayUseCase.invoke(Unit)

            if (TimeUtility.format(Date()) == (_lastDay.value?.currentDateString ?: "show")) {

                _hideAddButton.value = HideAddButtonState.Smile(lastDay.value?.imageId!!)

            } else {

                _hideAddButton.value = HideAddButtonState.Add

            }

            Timber.i("fix! sum viewModel: ${_hideAddButton.value}")
        }
    }


}