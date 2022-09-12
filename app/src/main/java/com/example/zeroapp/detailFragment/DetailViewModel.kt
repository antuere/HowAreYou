package com.example.zeroapp.detailFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.util.MyExtendedViewModel
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(override var databaseDao: DayDatabaseDao, val dayId: Long) :
    MyExtendedViewModel() {

    private val _currentDay = MutableLiveData<Day?>()
    val currentDay: LiveData<Day?>
        get() = _currentDay


    private val _navigateToHistory = MutableLiveData(false)
    val navigateToHistory: LiveData<Boolean>
        get() = _navigateToHistory

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


    fun navigateDone() {
        _navigateToHistory.value = false
    }

    override fun deleteDay(id: Long) {
        super.deleteDay(id)
        _navigateToHistory.value = true
    }
}