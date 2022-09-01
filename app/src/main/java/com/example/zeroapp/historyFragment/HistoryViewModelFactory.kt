package com.example.zeroapp.historyFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zeroapp.dataBase.DayDatabaseDao
import com.example.zeroapp.titleFragment.TitleFragmentViewModel

class HistoryViewModelFactory(val databaseDao: DayDatabaseDao) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}