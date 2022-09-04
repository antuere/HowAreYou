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
    ViewModel(), DayClickListener {

    var listDays: LiveData<List<Day>> = databaseDao.getAllDays()

    private var _navigateToDetail = MutableLiveData<Long?>()
    val navigateToDetail: LiveData<Long?>
        get() = _navigateToDetail

    private var _deleteItem = MutableLiveData<Long?>()
    val deleteItem: LiveData<Long?>
        get() = _deleteItem

    fun navigateDone() {
        _navigateToDetail.value = null
        _deleteItem.value = null
    }

    override fun onClick(day: Day) {
        _navigateToDetail.value = day.dayId
    }

    override fun onClickLong(day: Day) {
//        _deleteItem.value = day.dayId
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.deleteDay(day.dayId)
            }
        }
    }
}


