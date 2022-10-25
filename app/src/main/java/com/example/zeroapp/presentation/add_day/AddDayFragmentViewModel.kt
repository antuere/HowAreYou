package com.example.zeroapp.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.AddDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDayFragmentViewModel @Inject constructor(private val addDayUseCase: AddDayUseCase) :
    ViewModel() {

    fun onSmileClicked(imageId: Int, descDay: String) {
        val day = Day(imageId = imageId, dayText = descDay)

        viewModelScope.launch {
            addDayUseCase.invoke(day)
        }
    }

}