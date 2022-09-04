package com.example.zeroapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MyExtendedViewModel : ViewModel() {

    open lateinit var databaseDao: DayDatabaseDao

    fun deleteDay(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.deleteDay(id)
            }
        }
    }
}