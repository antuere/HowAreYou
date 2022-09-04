package com.example.zeroapp.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DetailViewModel(val databaseDao: DayDatabaseDao, val dayId: Long) : ViewModel() {

    private val _currentDay = MutableLiveData<Day?>()
    val currentDay: LiveData<Day?>
        get() = _currentDay


    init {
        getDay()
    }

    private fun getDay() {
        viewModelScope.launch {
            _currentDay.value = getDayByIdFromBD(dayId)
        }
    }

    private suspend fun getDayByIdFromBD(id: Long): Day? {
        return withContext(Dispatchers.IO) {
            databaseDao.getDayById(id)
        }
    }

    fun deleteDay() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                databaseDao.deleteDay(_currentDay.value!!.dayId)
            }
        }
    }
}