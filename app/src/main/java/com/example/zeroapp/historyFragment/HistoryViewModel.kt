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

    var listDays: LiveData<List<Day>> = databaseDao.getAllDays()

    private var _navigateToDetail = MutableLiveData<Long?>()
    val navigateToDetail: LiveData<Long?>
        get() = _navigateToDetail

    fun smileOnClick(dayId : Long){
        _navigateToDetail.value = dayId
    }

    fun navigateDone(){
        _navigateToDetail.value = null
    }

}