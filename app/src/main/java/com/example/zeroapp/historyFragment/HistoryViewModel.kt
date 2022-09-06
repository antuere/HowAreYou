package com.example.zeroapp.historyFragment

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.*
import com.example.zeroapp.MyExtendedViewModel
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import com.example.zeroapp.showAlertDialog
import androidx.fragment.app.Fragment
import timber.log.Timber

class HistoryViewModel(override var databaseDao: DayDatabaseDao) :
    MyExtendedViewModel() {

    var listDays: LiveData<List<Day>> = databaseDao.getAllDays()

}


