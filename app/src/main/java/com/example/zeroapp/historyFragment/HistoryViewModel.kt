package com.example.zeroapp.historyFragment

import androidx.lifecycle.*
import com.example.zeroapp.util.MyExtendedViewModel
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao

class HistoryViewModel(override var databaseDao: DayDatabaseDao) :
    MyExtendedViewModel() {

    var listDays: LiveData<List<Day>> = liveData {
        databaseDao.getAllDays().collect {
            emit(it)
        }
    }

}


