package com.example.zeroapp.historyFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(val databaseDao: DayDatabaseDao) : ViewModel() {
    private var _listDays = MutableLiveData<List<Day>>()
    val listDays: LiveData<List<Day>>
        get() = _listDays


    init {
        getDays()
    }

    private fun getDays() {
        viewModelScope.launch {
            _listDays.value = getDaysFromDB()
        }
    }

    private suspend fun getDaysFromDB(): List<Day>? {
        return withContext(Dispatchers.IO) {
            databaseDao.getAllDays().value
        }
    }
}