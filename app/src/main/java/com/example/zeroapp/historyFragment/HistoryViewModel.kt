package com.example.zeroapp.historyFragment

import android.app.Application
import androidx.lifecycle.*
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HistoryViewModel(val databaseDao: DayDatabaseDao) :
    ViewModel() {

    var listDays = databaseDao.getAllDays()


}