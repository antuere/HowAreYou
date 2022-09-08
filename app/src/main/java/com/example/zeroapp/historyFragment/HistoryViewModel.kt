package com.example.zeroapp.historyFragment

import androidx.lifecycle.*
import com.example.zeroapp.MyExtendedViewModel
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao

class HistoryViewModel(override var databaseDao: DayDatabaseDao) :
    MyExtendedViewModel() {

    var listDays: LiveData<List<Day>> = databaseDao.getAllDays()
}


