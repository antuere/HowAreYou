package com.example.zeroapp.summaryFragment

import android.text.format.DateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SummaryViewModel(private val databaseDao: DayDatabaseDao) : ViewModel() {

    private var _lastDay = MutableLiveData<Day?>()
    val lastDay: LiveData<Day?>
        get() = _lastDay

    private val _hideAddButton = MutableLiveData(false)
    val hideAddButton: LiveData<Boolean>
        get() = _hideAddButton

    init {
        Timber.i("fix! sum viewModel: init sumModel")
        updateLastDay()
    }

    fun updateLastDay() {
        viewModelScope.launch {
            Timber.i("fix! sum viewModel: enter in upd")

            val currentDate = DateFormat.format("dd/MM/yy", System.currentTimeMillis()).toString()
            _lastDay.value = getDayFromDB()

            Timber.i("fix! sum viewModel: change in upd")

            _hideAddButton.value = currentDate == (_lastDay.value?.currentDate ?: "offWish")

            Timber.i("fix! sum viewModel: ${_hideAddButton.value}")
        }
    }


    private suspend fun getDayFromDB(): Day? {
        return withContext(Dispatchers.IO) {
            databaseDao.getDay()
        }
    }


}