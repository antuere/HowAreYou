package com.example.zeroapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class MyExtendedViewModel : ViewModel() {

    open lateinit var databaseDao: DayDatabaseDao

    fun deleteDay(id: Long) {
        viewModelScope.launch {
            Timber.i("fix! ext viewModel: enter delete")
            withContext(Dispatchers.IO) {
                databaseDao.deleteDay(id)
            }
            Timber.i("fix! ext viewModel: exit delete")
        }

    }
}