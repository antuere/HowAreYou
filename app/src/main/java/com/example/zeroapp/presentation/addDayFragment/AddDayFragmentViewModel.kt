package com.example.zeroapp.presentation.addDayFragment


import androidx.lifecycle.*
import antuere.domain.Day
import antuere.domain.usecases.AddDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddDayFragmentViewModel @Inject constructor(private val addDayUseCase: AddDayUseCase) :
    ViewModel() {

    fun smileClicked(imageId: Int, descDay: String) {
        Timber.i("my log smile click")
        val day = Day(imageId = imageId, dayText = descDay)
        viewModelScope.launch {
            addDayUseCase.invoke(day)
        }
    }


}