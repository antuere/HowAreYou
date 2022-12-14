package com.example.zeroapp.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.AddDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDayFragmentViewModel @Inject constructor(private val addDayUseCase: AddDayUseCase) :
    ViewModel() {

    fun onClickSmile(imageName: String, descDay: String) {
        val day = Day(imageName = imageName, dayText = descDay)

        viewModelScope.launch {
            addDayUseCase(day)
        }
    }

}