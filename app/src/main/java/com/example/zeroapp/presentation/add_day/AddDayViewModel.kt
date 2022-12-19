package com.example.zeroapp.presentation.add_day


import androidx.lifecycle.*
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.AddDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddDayViewModel @Inject constructor(
    private val addDayUseCase: AddDayUseCase
    ) : ViewModel() {

    fun onClickSmile(imageResId: Int, descDay: String) {
        val day = Day(imageResId = imageResId, dayText = descDay)

        viewModelScope.launch {
            Timber.i("add day bug : start added day in vm - $day")
            addDayUseCase(day)
        }
    }

}