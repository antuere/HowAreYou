package com.example.zeroapp.presentation.historyFragment

import androidx.lifecycle.*
import com.example.zeroapp.util.MyExtendedViewModel
import antuere.domain.Day
import antuere.domain.repository.DayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(override var dayRepository: DayRepository) :
    MyExtendedViewModel() {

    var listDays: LiveData<List<Day>> = dayRepository.getAllDays().asLiveData(Dispatchers.Main)

}


