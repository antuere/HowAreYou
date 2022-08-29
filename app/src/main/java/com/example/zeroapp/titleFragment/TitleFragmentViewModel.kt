package com.example.zeroapp.titleFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TitleFragmentViewModel(val databaseDao: DayDatabaseDao, val descDay: String) : ViewModel() {


    private val lastDay = MutableLiveData<Day?>()

    init {
        getLastDay()
    }

    private fun getLastDay() {
        viewModelScope.launch {
            lastDay.value = getDayFromDB()
        }
    }

    private suspend fun getDayFromDB(): Day? {
        return withContext(Dispatchers.IO) {
            databaseDao.getDay()
        }
    }


    fun smileClicked(imageId: Int) {
        val day = Day(imageId = imageId, dayText = descDay)
        if (lastDay.value == null) {
            insert(day)
        } else {
            if (day.currentDate == lastDay.value!!.currentDate) {
                update(day)
            } else insert(day)

        }
    }


    private fun insert(day: Day) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.insert(day)
            }
        }
    }

    private fun update(day: Day) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.update(day)
            }
        }
    }


}