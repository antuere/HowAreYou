package com.example.zeroapp.historyFragment

import android.text.format.DateFormat
import androidx.lifecycle.*
import com.example.zeroapp.MyExtendedViewModel
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HistoryViewModel(override var databaseDao: DayDatabaseDao) :
    MyExtendedViewModel() {



    var listDays: LiveData<List<Day>> = liveData {
        databaseDao.getAllDays().collect{
            emit(it)
        }
    }




}


