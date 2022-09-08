package com.example.zeroapp.addDayFragment


import androidx.lifecycle.*
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AddDayFragmentViewModel(private val databaseDao: DayDatabaseDao) :
    ViewModel() {


    fun smileClicked(imageId: Int, descDay: String) {
        Timber.i("my log smile click")
        val day = Day(imageId = imageId, dayText = descDay)
        insert(day)
    }


    private fun insert(day: Day) {
        Timber.i("my log day insert BD")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.insert(day)
            }
        }
    }



}