package com.example.zeroapp.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.AddDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDayViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase
    ) : ViewModel() {

    fun onClickSmile(imageResId: Int, descDay: String) {
        val day = Day(imageResId = imageResId, dayText = descDay)

        viewModelScope.launch(Dispatchers.IO) {
            addDayUseCase(day)
        }
    }

}