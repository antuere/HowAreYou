package com.example.zeroapp.historyFragment

import androidx.lifecycle.*
import com.example.zeroapp.MyExtendedViewModel
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import com.example.zeroapp.showAlertDialog

class HistoryViewModel(override var databaseDao: DayDatabaseDao) :
    MyExtendedViewModel(), DayClickListener {

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
        _deleteItem.value = day.dayId
    }

    fun resetDeleteItem() {
        _deleteItem.value = null
    }
}


