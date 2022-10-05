package com.example.zeroapp.presentation.addDayFragment


import androidx.lifecycle.*
import antuere.domain.Day
import antuere.domain.repository.DayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddDayFragmentViewModel @Inject constructor(private val dayRepository: DayRepository) :
    ViewModel() {


    fun smileClicked(imageId: Int, descDay: String) {
        Timber.i("my log smile click")
        val day = Day(imageId = imageId, dayText = descDay)
        viewModelScope.launch {
            dayRepository.insert(day)
        }
    }


}