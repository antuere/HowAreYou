package com.example.zeroapp.presentation.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.Day
import antuere.domain.usecases.UpdateLastDayUseCase
import antuere.domain.util.TimeUtility
import com.example.zeroapp.util.WishAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val updateLastDayUseCase: UpdateLastDayUseCase,
    private val wishAnalyzer: WishAnalyzer
) :
    ViewModel() {

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private val _hideAddButton = MutableLiveData<HideAddButtonState>(HideAddButtonState.Add)
    val hideAddButton: LiveData<HideAddButtonState>
        get() = _hideAddButton

    private var _wishText = MutableLiveData<String>()
    val wishText: LiveData<String>
        get() = _wishText

    init {
        updateInfo()
    }

    fun updateInfo() {
        viewModelScope.launch {

            _lastDay.value = updateLastDayUseCase.invoke(Unit)

            if (TimeUtility.format(Date()) == (_lastDay.value?.dateString ?: "show")) {

                _hideAddButton.value = HideAddButtonState.Smile(lastDay.value?.imageId!!)

                _wishText.value = wishAnalyzer.getWishString(lastDay.value?.imageId!!)

            } else {

                _hideAddButton.value = HideAddButtonState.Add
                _wishText.value = wishAnalyzer.getWishString(-1)

            }
        }
    }


}