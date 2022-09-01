package com.example.zeroapp.titleFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TitleFragmentViewModel(val databaseDao: DayDatabaseDao) : ViewModel() {


    private val lastDay = MutableLiveData<Day?>()

    init {
        getLastDay()
    }

    private fun getLastDay() {
        Timber.i("my log get last day from BD")
        viewModelScope.launch {
            lastDay.value = getDayFromDB()
        }
    }

    private suspend fun getDayFromDB(): Day? {
        return withContext(Dispatchers.IO) {
            databaseDao.getDay()
        }
    }


    fun smileClicked(imageId: Int, descDay : String) {
        Timber.i("my log smile click")
        val day = Day(imageId = imageId, dayText = descDay)
        if (lastDay.value == null) {
            Timber.i("my log day null")
            insert(day)
        } else {
            Timber.i("my log day not null")
            if (day.currentDate == lastDay.value!!.currentDate) {
                Timber.i("my log day repeat")
                update(day)
            } else insert(day)

        }
    }


    private fun insert(day: Day) {
        Timber.i("my log day insert BD")
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