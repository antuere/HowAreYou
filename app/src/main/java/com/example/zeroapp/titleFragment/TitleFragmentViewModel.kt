package com.example.zeroapp.titleFragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TitleFragmentViewModel(val databaseDao: DayDatabaseDao, application: Application) :
    AndroidViewModel(application) {


    private val _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    init {
        getLastDay()
    }

    private fun getLastDay() {
        viewModelScope.launch {
            _lastDay.value = getDayFromDB()
            Timber.i("my log get last day from BD, its ${_lastDay.value?.currentDate ?: "null"}")
        }
    }

    private suspend fun getDayFromDB(): Day? {
        return withContext(Dispatchers.IO) {
            databaseDao.getDay()
        }
    }


    fun smileClicked(imageId: Int, descDay: String) {
        Timber.i("my log smile click")
        val day = Day(imageId = imageId, dayText = descDay)
        if (_lastDay.value == null) {
            Timber.i("my log day null")
            insertAndUpdateLastDay(day)
        } else {
            Timber.i("my log day not null, ${_lastDay.value!!.currentDate}")
            if (day.currentDate == _lastDay.value!!.currentDate) {
                Timber.i("my log day repeat")
                Toast.makeText(
                    getApplication(),
                    "Today you already have write",
                    Toast.LENGTH_SHORT
                ).show()
            } else insertAndUpdateLastDay(day)

        }
    }


    private fun insertAndUpdateLastDay(day: Day) {
        Timber.i("my log day insert BD")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.insert(day)
                getLastDay()
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