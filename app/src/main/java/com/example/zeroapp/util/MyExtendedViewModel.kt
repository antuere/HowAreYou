package com.example.zeroapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class MyExtendedViewModel : ViewModel() {

    open lateinit var dayRepository: DayRepository

    open fun deleteDay(id: Long) {
        viewModelScope.launch {
            Timber.i("fix! ext viewModel: enter delete")
            dayRepository.deleteDay(id)
            Timber.i("fix! ext viewModel: exit delete")
        }

    }
}