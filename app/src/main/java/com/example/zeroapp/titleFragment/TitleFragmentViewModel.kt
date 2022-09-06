package com.example.zeroapp.titleFragment

import android.app.Application
import android.text.format.DateFormat
import androidx.lifecycle.*
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class TitleFragmentViewModel(private val databaseDao: DayDatabaseDao, application: Application) :
    AndroidViewModel(application) {


    private var _lastDay = MutableLiveData<Day?>()

    private val _showToast = MutableLiveData(false)
    val showToast: LiveData<Boolean>
        get() = _showToast

    private val _showWish = MutableLiveData(false)
    val showWish: LiveData<Boolean>
        get() = _showWish

    init {
        Timber.i("my log init TitleModel")
        updateLastDay()
    }

    fun smileClicked(imageId: Int, descDay: String) {
        _showWish.value = true
        Timber.i("my log smile click")
        val day = Day(imageId = imageId, dayText = descDay)
        if (_lastDay.value == null) {
            Timber.i("my log day null")
            insertAndUpdateLastDay(day)
        } else {
            Timber.i("my log day not null, ${_lastDay.value!!.currentDate}")
            if (day.currentDate == _lastDay.value!!.currentDate) {
                Timber.i("my log day repeat")
                _showToast.value = true
            } else insertAndUpdateLastDay(day)

        }
    }

    fun updateLastDay() {
        viewModelScope.launch {
            val currentDate = DateFormat.format("dd/MM/yy", System.currentTimeMillis()).toString()
            _lastDay.value = getDayFromDB()
            if (currentDate != (_lastDay.value?.currentDate ?: "test")) {
                _showWish.value = false
            }
            Timber.i("my log get last day from BD, its ${_lastDay.value?.currentDate ?: "null"}")
        }
    }


    private suspend fun getDayFromDB(): Day? {
        return withContext(Dispatchers.IO) {
            databaseDao.getDay()
        }
    }

    private fun insertAndUpdateLastDay(day: Day) {
        Timber.i("my log day insert BD")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.insert(day)
                updateLastDay()
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

    fun showToastReset() {
        _showToast.value = false
    }


}