package com.example.zeroapp.detailFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zeroapp.dataBase.DayDatabaseDao
import com.example.zeroapp.historyFragment.HistoryViewModel

class DetailViewModelFactory(val databaseDao: DayDatabaseDao, val dayId: Long) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(databaseDao, dayId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}