package com.example.zeroapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class MyExtendedViewModel : ViewModel() {

    open lateinit var databaseDao: DayDatabaseDao

    open fun deleteDay(id: Long) {
        viewModelScope.launch {
            Timber.i("fix! ext viewModel: enter delete")
            withContext(Dispatchers.IO) {
                databaseDao.deleteDay(id)
            }
            Timber.i("fix! ext viewModel: exit delete")
        }

    }
}